package cn.anlper.train.service;

import cn.anlper.train.entities.SkToken;
import cn.anlper.train.mapper.SkTokenMapper;
import cn.anlper.train.req.SkTokenQueryReq;
import cn.anlper.train.req.SkTokenSaveReq;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.SkTokenQueryResp;
import cn.anlper.train.utils.SnowFlake;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SkTokenService {
    @Resource
    private SkTokenMapper skTokenMapper;
    @Resource
    private DailyTrainSeatService dailyTrainSeatService;
    @Resource
    private DailyTrainStationService dailyTrainStationService;

    @Resource
    private SnowFlake snowFlake;
    public void save(SkTokenSaveReq req) {
        DateTime now = DateTime.now();
        SkToken skToken = BeanUtil.copyProperties(req, SkToken.class);
        if (ObjUtil.isNull(skToken.getId())) {
            skToken.setId(snowFlake.nextId());
            skToken.setCreateTime(now);
            skToken.setUpdateTime(now);
            skTokenMapper.insert(skToken);
        } else {
            skToken.setUpdateTime(now);
            skTokenMapper.updateByPrimaryKey(skToken);
        }

    }

    public PageResp queryList(SkTokenQueryReq req) {
        Example example = new Example(SkToken.class);
        PageHelper.startPage(req.getPage(), req.getSize());

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        List<SkToken> skTokenList = skTokenMapper.selectByExample(example);
        PageInfo<SkToken> pageInfo = new PageInfo<>(skTokenList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<SkTokenQueryResp> skTokenQueryRespList = BeanUtil.copyToList(skTokenList, SkTokenQueryResp.class);
        PageResp<SkTokenQueryResp> pageResp = new PageResp<>(skTokenQueryRespList, pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        skTokenMapper.deleteByPrimaryKey(id);
    }

    public void genDailyToken(Date date, String trainCode) {
        log.info("删除日期{} 车次{}的令牌记录", DateUtil.formatDate(date), trainCode);
        Example example = new Example(SkToken.class);
        example.createCriteria().andEqualTo("date", date).andEqualTo("trainCode", trainCode);
        skTokenMapper.deleteByExample(example);

        SkToken skToken = new SkToken();
        Date now = new Date();
        skToken.setId(snowFlake.nextId());
        skToken.setDate(date);
        skToken.setTrainCode(trainCode);

        skToken.setCreateTime(now);
        skToken.setUpdateTime(now);

        int seatCount = dailyTrainSeatService.countSeat(date, trainCode);
        log.info("车次【{}】的座位数为：{}", trainCode, seatCount);
        int stationCount = dailyTrainStationService.countByTrainCode(date, trainCode);
        log.info("车次【{}】的站数为：{}", trainCode, stationCount);
        int count = seatCount * stationCount;
        skToken.setCount(count);
        log.info("车次【{}】初始生成的令牌书为：{}", trainCode, count);
        skTokenMapper.insert(skToken);
    }
}
