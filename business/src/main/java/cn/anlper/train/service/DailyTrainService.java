package cn.anlper.train.service;

import cn.anlper.train.entities.DailyTrain;
import cn.anlper.train.mapper.DailyTrainMapper;
import cn.anlper.train.req.DailyTrainQueryReq;
import cn.anlper.train.req.DailyTrainSaveReq;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.DailyTrainQueryResp;
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
public class DailyTrainService {
    @Resource
    private DailyTrainMapper dailyTrainMapper;

    @Resource
    private SnowFlake snowFlake;
    public void save(DailyTrainSaveReq req) {
        DateTime now = DateTime.now();
        DailyTrain dailyTrain = BeanUtil.copyProperties(req, DailyTrain.class);
        if (ObjUtil.isNull(dailyTrain.getId())) {
            dailyTrain.setId(snowFlake.nextId());
            dailyTrain.setCreateTime(now);
            dailyTrain.setUpdateTime(now);
            dailyTrainMapper.insert(dailyTrain);
        } else {
            dailyTrain.setUpdateTime(now);
            dailyTrainMapper.updateByPrimaryKey(dailyTrain);
        }

    }

    public PageResp queryList(DailyTrainQueryReq req) {
        Example example = new Example(DailyTrain.class);
        PageHelper.startPage(req.getPage(), req.getSize());

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        List<DailyTrain> dailyTrainList = dailyTrainMapper.selectByExample(example);
        PageInfo<DailyTrain> pageInfo = new PageInfo<>(dailyTrainList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainQueryResp> dailyTrainQueryRespList = BeanUtil.copyToList(dailyTrainList, DailyTrainQueryResp.class);
        PageResp<DailyTrainQueryResp> pageResp = new PageResp<>(dailyTrainQueryRespList, pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        dailyTrainMapper.deleteByPrimaryKey(id);
    }
}
