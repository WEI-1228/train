package cn.anlper.train.service;

import cn.anlper.train.entities.TrainCarriage;
import cn.anlper.train.enums.SeatColEnum;
import cn.anlper.train.mapper.TrainCarriageMapper;
import cn.anlper.train.req.TrainCarriageQueryReq;
import cn.anlper.train.req.TrainCarriageSaveReq;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.TrainCarriageQueryResp;
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
public class TrainCarriageService {
    @Resource
    private TrainCarriageMapper trainCarriageMapper;

    @Resource
    private SnowFlake snowFlake;
    public void save(TrainCarriageSaveReq req) {
        DateTime now = DateTime.now();

        List<SeatColEnum> colsByType = SeatColEnum.getColsByType(req.getSeatType());
        req.setColCount(colsByType.size());
        req.setSeatCount(req.getColCount() * req.getRowCount());

        TrainCarriage trainCarriage = BeanUtil.copyProperties(req, TrainCarriage.class);
        if (ObjUtil.isNull(trainCarriage.getId())) {
            trainCarriage.setId(snowFlake.nextId());
            trainCarriage.setCreateTime(now);
            trainCarriage.setUpdateTime(now);
            trainCarriageMapper.insert(trainCarriage);
        } else {
            trainCarriage.setUpdateTime(now);
            trainCarriageMapper.updateByPrimaryKey(trainCarriage);
        }

    }

    public PageResp queryList(TrainCarriageQueryReq req) {
        Example example = new Example(TrainCarriage.class);
        example.setOrderByClause("train_code asc, indexes asc");
        Example.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andEqualTo("trainCode", req.getTrainCode());
        }
        PageHelper.startPage(req.getPage(), req.getSize());

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        List<TrainCarriage> trainCarriageList = trainCarriageMapper.selectByExample(example);
        PageInfo<TrainCarriage> pageInfo = new PageInfo<>(trainCarriageList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<TrainCarriageQueryResp> trainCarriageQueryRespList = BeanUtil.copyToList(trainCarriageList, TrainCarriageQueryResp.class);
        PageResp<TrainCarriageQueryResp> pageResp = new PageResp<>(trainCarriageQueryRespList, pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        trainCarriageMapper.deleteByPrimaryKey(id);
    }

    public List<TrainCarriage> selectByTrainCode(String trainCode) {
        Example example = new Example(TrainCarriage.class);
        example.setOrderByClause("indexes asc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("trainCode", trainCode);
        return trainCarriageMapper.selectByExample(example);
    }
}
