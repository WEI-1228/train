package cn.anlper.train.service;

import cn.anlper.train.entities.DailyTrain;
import cn.anlper.train.entities.Train;
import cn.anlper.train.mapper.DailyTrainMapper;
import cn.anlper.train.req.DailyTrainQueryReq;
import cn.anlper.train.req.DailyTrainSaveReq;
import cn.anlper.train.resp.DailyTrainQueryResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.utils.SnowFlake;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
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
public class DailyTrainService {
    @Resource
    private DailyTrainMapper dailyTrainMapper;
    @Resource
    private TrainService trainService;
    @Resource
    private DailyTrainStationService dailyTrainStationService;
    @Resource
    private DailyTrainCarriageService dailyTrainCarriageService;
    @Resource
    private DailyTrainSeatService dailyTrainSeatService;

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

    /**
     * 生成某日所有车次信息，包括车次、车站、车厢、座位
     * @param date
     */
    public void genDaily(Date date) {
        List<Train> trainList = trainService.selectAll();
        if (CollUtil.isEmpty(trainList)) {
            log.info("没有车次数据，任务结束");
            return;
        }

        for (Train train: trainList) {
            genDailyTrain(date, train);
            dailyTrainStationService.genDailyTrainStation(date, train.getCode());
            dailyTrainCarriageService.genDailyTrainCarriage(date, train.getCode());
            dailyTrainSeatService.genDailyTrainSeat(date, train.getCode());
        }
    }

    public void genDailyTrain(Date date, Train train) {
        log.info("开始生成日期【{}】，车次【{}】的信息", DateUtil.formatDate(date), train.getCode());
        // 删除该车次当天已有的数据
        Example example = new Example(DailyTrain.class);
        example.createCriteria()
                .andEqualTo("dailyDate", date)
                .andEqualTo("code", train.getCode());
        dailyTrainMapper.deleteByExample(example);

        // 生成该车次数据
        DateTime now = DateTime.now();
        DailyTrain dailyTrain = BeanUtil.copyProperties(train, DailyTrain.class);
        dailyTrain.setDailyDate(date);
        dailyTrain.setId(snowFlake.nextId());
        dailyTrain.setCreateTime(now);
        dailyTrain.setUpdateTime(now);
        dailyTrainMapper.insert(dailyTrain);
        log.info("生成日期【{}】，车次【{}】的信息结束", DateUtil.formatDate(date), train.getCode());
    }
}
