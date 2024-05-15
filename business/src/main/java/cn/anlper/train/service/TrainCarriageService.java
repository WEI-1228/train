package cn.anlper.train.service;

import cn.anlper.train.entities.TrainCarriage;
import cn.anlper.train.mapper.TrainCarriageMapper;
import cn.anlper.train.req.TrainCarriageQueryReq;
import cn.anlper.train.req.TrainCarriageSaveReq;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.TrainCarriageQueryResp;
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
public class TrainCarriageService {
    @Resource
    private TrainCarriageMapper trainCarriageMapper;

    @Resource
    private SnowFlake snowFlake;
    public void save(TrainCarriageSaveReq req) {
        DateTime now = DateTime.now();
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
}
