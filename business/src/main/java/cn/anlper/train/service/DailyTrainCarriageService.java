package cn.anlper.train.service;

import cn.anlper.train.entities.DailyTrainCarriage;
import cn.anlper.train.entities.TrainCarriage;
import cn.anlper.train.enums.SeatColEnum;
import cn.anlper.train.mapper.DailyTrainCarriageMapper;
import cn.anlper.train.req.DailyTrainCarriageQueryReq;
import cn.anlper.train.req.DailyTrainCarriageSaveReq;
import cn.anlper.train.resp.DailyTrainCarriageQueryResp;
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
public class DailyTrainCarriageService {
    @Resource
    private DailyTrainCarriageMapper dailyTrainCarriageMapper;
    @Resource
    private TrainCarriageService trainCarriageService;

    @Resource
    private SnowFlake snowFlake;
    public void save(DailyTrainCarriageSaveReq req) {
        DateTime now = DateTime.now();

        List<SeatColEnum> colsByType = SeatColEnum.getColsByType(req.getSeatType());
        req.setColCount(colsByType.size());
        req.setSeatCount(req.getRowCount() * req.getColCount());

        DailyTrainCarriage dailyTrainCarriage = BeanUtil.copyProperties(req, DailyTrainCarriage.class);
        if (ObjUtil.isNull(dailyTrainCarriage.getId())) {
            dailyTrainCarriage.setId(snowFlake.nextId());
            dailyTrainCarriage.setCreateTime(now);
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriageMapper.insert(dailyTrainCarriage);
        } else {
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriageMapper.updateByPrimaryKey(dailyTrainCarriage);
        }

    }

    public PageResp queryList(DailyTrainCarriageQueryReq req) {
        Example example = new Example(DailyTrainCarriage.class);
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
        List<DailyTrainCarriage> dailyTrainCarriageList = dailyTrainCarriageMapper.selectByExample(example);
        PageInfo<DailyTrainCarriage> pageInfo = new PageInfo<>(dailyTrainCarriageList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainCarriageQueryResp> dailyTrainCarriageQueryRespList = BeanUtil.copyToList(dailyTrainCarriageList, DailyTrainCarriageQueryResp.class);
        PageResp<DailyTrainCarriageQueryResp> pageResp = new PageResp<>(dailyTrainCarriageQueryRespList, pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        dailyTrainCarriageMapper.deleteByPrimaryKey(id);
    }

    public void genDailyTrainCarriage(Date date, String trainCode) {
        log.info("生成日期【{}】，车次【{}】的车厢信息开始", DateUtil.formatDate(date), trainCode);
        Example example = new Example(DailyTrainCarriage.class);
        example.createCriteria()
                .andEqualTo("dailyDate", date)
                .andEqualTo("trainCode", trainCode);
        dailyTrainCarriageMapper.deleteByExample(example);

        List<TrainCarriage> trainCarriageList = trainCarriageService.selectByTrainCode(trainCode);
        if (CollUtil.isEmpty(trainCarriageList)) {
            log.info("该车次没有车厢基础数据，生成该车次的车厢信息结束");
            return;
        }

        for (TrainCarriage trainCarriage: trainCarriageList) {
            Date now = new Date();
            DailyTrainCarriage dailyTrainCarriage = BeanUtil.copyProperties(trainCarriage, DailyTrainCarriage.class);
            dailyTrainCarriage.setDailyDate(date);
            dailyTrainCarriage.setId(snowFlake.nextId());
            dailyTrainCarriage.setCreateTime(now);
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriageMapper.insert(dailyTrainCarriage);
        }
        log.info("生成日期【{}】，车次【{}】的车厢信息结束", DateUtil.formatDate(date), trainCode);
    }
}
