package cn.anlper.train.service;

import cn.anlper.train.entities.DailyTrainSeat;
import cn.anlper.train.mapper.DailyTrainSeatMapper;
import cn.anlper.train.req.DailyTrainSeatQueryReq;
import cn.anlper.train.req.DailyTrainSeatSaveReq;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.DailyTrainSeatQueryResp;
import cn.anlper.train.utils.SnowFlake;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Slf4j
public class DailyTrainSeatService {
    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;

    @Resource
    private SnowFlake snowFlake;
    public void save(DailyTrainSeatSaveReq req) {
        DateTime now = DateTime.now();
        DailyTrainSeat dailyTrainSeat = BeanUtil.copyProperties(req, DailyTrainSeat.class);
        if (ObjUtil.isNull(dailyTrainSeat.getId())) {
            dailyTrainSeat.setId(snowFlake.nextId());
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        } else {
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.updateByPrimaryKey(dailyTrainSeat);
        }

    }

    public PageResp queryList(DailyTrainSeatQueryReq req) {
        Example example = new Example(DailyTrainSeat.class);
        PageHelper.startPage(req.getPage(), req.getSize());

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        List<DailyTrainSeat> dailyTrainSeatList = dailyTrainSeatMapper.selectByExample(example);
        PageInfo<DailyTrainSeat> pageInfo = new PageInfo<>(dailyTrainSeatList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainSeatQueryResp> dailyTrainSeatQueryRespList = BeanUtil.copyToList(dailyTrainSeatList, DailyTrainSeatQueryResp.class);
        PageResp<DailyTrainSeatQueryResp> pageResp = new PageResp<>(dailyTrainSeatQueryRespList, pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        dailyTrainSeatMapper.deleteByPrimaryKey(id);
    }
}
