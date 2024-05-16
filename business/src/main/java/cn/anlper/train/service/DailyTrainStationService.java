package cn.anlper.train.service;

import cn.anlper.train.entities.DailyTrainStation;
import cn.anlper.train.mapper.DailyTrainStationMapper;
import cn.anlper.train.req.DailyTrainStationQueryReq;
import cn.anlper.train.req.DailyTrainStationSaveReq;
import cn.anlper.train.resp.DailyTrainStationQueryResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.utils.SnowFlake;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Slf4j
public class DailyTrainStationService {
    @Resource
    private DailyTrainStationMapper dailyTrainStationMapper;

    @Resource
    private SnowFlake snowFlake;
    public void save(DailyTrainStationSaveReq req) {
        DateTime now = DateTime.now();
        DailyTrainStation dailyTrainStation = BeanUtil.copyProperties(req, DailyTrainStation.class);
        if (ObjUtil.isNull(dailyTrainStation.getId())) {
            dailyTrainStation.setId(snowFlake.nextId());
            dailyTrainStation.setCreateTime(now);
            dailyTrainStation.setUpdateTime(now);
            dailyTrainStationMapper.insert(dailyTrainStation);
        } else {
            dailyTrainStation.setUpdateTime(now);
            dailyTrainStationMapper.updateByPrimaryKey(dailyTrainStation);
        }

    }

    public PageResp queryList(DailyTrainStationQueryReq req) {
        Example example = new Example(DailyTrainStation.class);
        example.setOrderByClause("daily_date desc, train_code asc, indexes asc");
        Example.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andEqualTo("trainCode", req.getTrainCode());
        }
        if (ObjUtil.isNotEmpty(req.getDailyDate())) {
            criteria.andEqualTo("dailyDate", req.getDailyDate());
        }
        PageHelper.startPage(req.getPage(), req.getSize());

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        List<DailyTrainStation> dailyTrainStationList = dailyTrainStationMapper.selectByExample(example);
        PageInfo<DailyTrainStation> pageInfo = new PageInfo<>(dailyTrainStationList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainStationQueryResp> dailyTrainStationQueryRespList = BeanUtil.copyToList(dailyTrainStationList, DailyTrainStationQueryResp.class);
        PageResp<DailyTrainStationQueryResp> pageResp = new PageResp<>(dailyTrainStationQueryRespList, pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        dailyTrainStationMapper.deleteByPrimaryKey(id);
    }
}
