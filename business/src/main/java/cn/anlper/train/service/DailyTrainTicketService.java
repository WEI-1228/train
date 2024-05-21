package cn.anlper.train.service;

import cn.anlper.train.entities.DailyTrainTicket;
import cn.anlper.train.entities.TrainStation;
import cn.anlper.train.enums.SeatTypeEnum;
import cn.anlper.train.mapper.DailyTrainTicketMapper;
import cn.anlper.train.req.DailyTrainTicketQueryReq;
import cn.anlper.train.resp.DailyTrainTicketQueryResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.utils.SnowFlake;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DailyTrainTicketService {
    @Resource
    private DailyTrainTicketMapper dailyTrainTicketMapper;
    @Resource
    private TrainStationService trainStationService;
    @Resource
    private DailyTrainSeatService dailyTrainSeatService;

    @Resource
    private SnowFlake snowFlake;

    @Transactional
    public void genDailyTickets(Date date, String trainCode) {
        log.info("生成日期【{}】，车次【{}】的余票信息开始", DateUtil.formatDate(date), trainCode);
        Example example = new Example(DailyTrainTicket.class);
        example.createCriteria()
                .andEqualTo("trainCode", trainCode)
                .andEqualTo("dailyDate", date);
        dailyTrainTicketMapper.deleteByExample(example);

        List<TrainStation> trainStationList = trainStationService.selectByTrainCode(trainCode);
        if (CollUtil.isEmpty(trainStationList)) {
            log.info("该车次没有车站数据，生成该车次的余票信息结束");
        }

        Date now = new Date();

        for (int i = 0; i < trainStationList.size(); i++) {
            TrainStation startStation = trainStationList.get(i);
            for (int j = i + 1; j < trainStationList.size(); j++) {
                TrainStation endStation = trainStationList.get(j);

                DailyTrainTicket dailyTrainTicket = new DailyTrainTicket();
                dailyTrainTicket.setId(snowFlake.nextId());
                dailyTrainTicket.setDailyDate(date);
                dailyTrainTicket.setTrainCode(trainCode);
                dailyTrainTicket.setStart(startStation.getName());
                dailyTrainTicket.setStartPinyin(startStation.getNamePinyin());
                dailyTrainTicket.setStartTime(startStation.getOutTime());
                dailyTrainTicket.setStartIndex(startStation.getIndexes());
                dailyTrainTicket.setEnd(endStation.getName());
                dailyTrainTicket.setEndPinyin(endStation.getNamePinyin());
                dailyTrainTicket.setEndTime(endStation.getInTime());
                dailyTrainTicket.setEndIndex(endStation.getIndexes());
                int ydz = dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.YDZ.getCode());
                int edz = dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.EDZ.getCode());
                int yw = dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.YW.getCode());
                int rw = dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.RW.getCode());

                dailyTrainTicket.setYdz(ydz);
                dailyTrainTicket.setYdzPrice(BigDecimal.ZERO);
                dailyTrainTicket.setEdz(edz);
                dailyTrainTicket.setEdzPrice(BigDecimal.ZERO);
                dailyTrainTicket.setRw(yw);
                dailyTrainTicket.setRwPrice(BigDecimal.ZERO);
                dailyTrainTicket.setYw(rw);
                dailyTrainTicket.setYwPrice(BigDecimal.ZERO);
                dailyTrainTicket.setCreateTime(now);
                dailyTrainTicket.setUpdateTime(now);
                dailyTrainTicketMapper.insert(dailyTrainTicket);
            }
        }
        log.info("生成日期【{}】，车次【{}】的余票信息结束", DateUtil.formatDate(date), trainCode);
    }

    public PageResp queryList(DailyTrainTicketQueryReq req) {
        Example example = new Example(DailyTrainTicket.class);
        Example.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andEqualTo("trainCode", req.getTrainCode());
        }
        if (ObjUtil.isNotNull(req.getDailyDate())) {
            criteria.andEqualTo("dailyDate", req.getDailyDate());
        }
        if (ObjUtil.isNotNull(req.getStart())) {
            criteria.andEqualTo("start", req.getStart());
        }
        if (ObjUtil.isNotNull(req.getEnd())) {
            criteria.andEqualTo("end", req.getEnd());
        }
        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainTicket> dailyTrainTickets = dailyTrainTicketMapper.selectByExample(example);
        PageInfo<DailyTrainTicket> pageInfo = new PageInfo<>(dailyTrainTickets);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainTicketQueryResp> ticketQueryResps = BeanUtil.copyToList(dailyTrainTickets, DailyTrainTicketQueryResp.class);
        return new PageResp<>(ticketQueryResps, pageInfo.getTotal());
    }
}
