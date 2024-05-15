package cn.anlper.train.service;

import cn.anlper.train.entities.TrainStation;
import cn.anlper.train.mapper.TrainStationMapper;
import cn.anlper.train.req.trainStationQueryReq;
import cn.anlper.train.req.trainStationSaveReq;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.TrainStationQueryResp;
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
public class TrainStationService {
    @Resource
    private TrainStationMapper trainStationMapper;

    @Resource
    private SnowFlake snowFlake;
    public void save(trainStationSaveReq req) {
        DateTime now = DateTime.now();
        TrainStation trainStation = BeanUtil.copyProperties(req, TrainStation.class);
        if (ObjUtil.isNull(trainStation.getId())) {
            trainStation.setId(snowFlake.nextId());
            trainStation.setCreateTime(now);
            trainStation.setUpdateTime(now);
            trainStationMapper.insert(trainStation);
        } else {
            trainStation.setUpdateTime(now);
            trainStationMapper.updateByPrimaryKey(trainStation);
        }

    }

    public PageResp queryList(trainStationQueryReq req) {
        Example example = new Example(TrainStation.class);
        example.setOrderByClause("train_code asc, indexes asc");
        Example.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andEqualTo("trainCode", req.getTrainCode());
        }

        PageHelper.startPage(req.getPage(), req.getSize());

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        List<TrainStation> trainStationList = trainStationMapper.selectByExample(example);
        PageInfo<TrainStation> pageInfo = new PageInfo<>(trainStationList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<TrainStationQueryResp> trainStationQueryRespList = BeanUtil.copyToList(trainStationList, TrainStationQueryResp.class);
        PageResp<TrainStationQueryResp> pageResp = new PageResp<>(trainStationQueryRespList, pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        trainStationMapper.deleteByPrimaryKey(id);
    }
}
