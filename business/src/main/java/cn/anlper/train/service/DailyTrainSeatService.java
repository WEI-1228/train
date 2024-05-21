package cn.anlper.train.service;

import cn.anlper.train.entities.DailyTrainSeat;
import cn.anlper.train.entities.TrainSeat;
import cn.anlper.train.entities.TrainStation;
import cn.anlper.train.mapper.DailyTrainSeatMapper;
import cn.anlper.train.req.DailyTrainSeatQueryReq;
import cn.anlper.train.req.DailyTrainSeatSaveReq;
import cn.anlper.train.resp.DailyTrainSeatQueryResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.utils.SnowFlake;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
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
public class DailyTrainSeatService {
    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;
    @Resource
    private TrainSeatService trainSeatService;
    @Resource
    private TrainStationService trainStationService;

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
        example.setOrderByClause("train_code asc, carriage_index asc, carriage_seat_index asc");
        Example.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andEqualTo("trainCode", req.getTrainCode());
        }
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

    public void genDailyTrainSeat(Date date, String trainCode) {
        log.info("生成日期【{}】，车次【{}】的座位信息开始", DateUtil.formatDate(date), trainCode);

        // 删除某日某车次的车站信息
        Example example = new Example(DailyTrainSeat.class);
        example.createCriteria()
                .andEqualTo("dailyDate", date)
                .andEqualTo("trainCode", trainCode);
        dailyTrainSeatMapper.deleteByExample(example);

        // 查询车次的车站信息
        List<TrainSeat> trainSeatList = trainSeatService.selectSeatByTrainCode(trainCode);
        if (CollUtil.isEmpty(trainSeatList)) {
            log.info("该车次没有座位基础数据，生成该车次的座位信息结束");
            return;
        }
        List<TrainStation> trainStationList = trainStationService.selectByTrainCode(trainCode);
        String sell = StrUtil.fillBefore("", '0', trainStationList.size() - 1);
        for (TrainSeat trainSeat: trainSeatList) {
            Date now = new Date();
            DailyTrainSeat dailyTrainSeat = BeanUtil.copyProperties(trainSeat, DailyTrainSeat.class);
            dailyTrainSeat.setDailyDate(date);
            dailyTrainSeat.setId(snowFlake.nextId());
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeat.setSell(sell);
            dailyTrainSeat.setDailyRow(trainSeat.getSeatRow());
            dailyTrainSeat.setDailyCol(trainSeat.getSeatCol());
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        }
        log.info("生成日期【{}】，车次【{}】的座位信息结束", DateUtil.formatDate(date), trainCode);
    }

    public int countSeat(Date date, String trainCode, String seatType) {
        Example example = new Example(DailyTrainSeat.class);
        example.createCriteria()
                .andEqualTo("dailyDate", date)
                .andEqualTo("trainCode", trainCode)
                .andEqualTo("seatType", seatType);
        return dailyTrainSeatMapper.selectCountByExample(example);
    }
}
