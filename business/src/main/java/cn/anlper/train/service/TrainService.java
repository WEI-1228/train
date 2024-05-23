package cn.anlper.train.service;

import cn.anlper.train.entities.Train;
import cn.anlper.train.mapper.TrainMapper;
import cn.anlper.train.req.TrainQueryReq;
import cn.anlper.train.req.TrainSaveReq;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.TrainQueryResp;
import cn.anlper.train.utils.SnowFlake;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Slf4j
public class TrainService {
    @Resource
    private TrainMapper trainMapper;

    @Resource
    private SnowFlake snowFlake;
    public void save(TrainSaveReq req) {
        DateTime now = DateTime.now();
        Train train = BeanUtil.copyProperties(req, Train.class);
        if (ObjUtil.isNull(train.getId())) {
            train.setId(snowFlake.nextId());
            train.setCreateTime(now);
            train.setUpdateTime(now);
            trainMapper.insert(train);
        } else {
            train.setUpdateTime(now);
            trainMapper.updateByPrimaryKey(train);
        }

    }

    @Transactional
    public PageResp queryList(TrainQueryReq req) {
        Example example = new Example(Train.class);
        PageHelper.startPage(req.getPage(), req.getSize());

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        List<Train> trainList = trainMapper.selectByExample(example);
        PageInfo<Train> pageInfo = new PageInfo<>(trainList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<TrainQueryResp> trainQueryRespList = BeanUtil.copyToList(trainList, TrainQueryResp.class);
        PageResp<TrainQueryResp> pageResp = new PageResp<>(trainQueryRespList, pageInfo.getTotal());
        return pageResp;
    }

//    @Transactional
    public List<TrainQueryResp> queryAll() {
        List<Train> trainList = selectAll();
        log.info("第二次查询");
        selectAll();
        return BeanUtil.copyToList(trainList, TrainQueryResp.class);
    }

    public List<Train> selectAll() {
        Example example = new Example(Train.class);
        example.setOrderByClause("code desc");
        return trainMapper.selectByExample(example);
    }

    public void delete(Long id) {
        trainMapper.deleteByPrimaryKey(id);
    }
}
