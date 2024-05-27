package cn.anlper.train.service;

import cn.anlper.train.entities.TrainCarriage;
import cn.anlper.train.entities.TrainSeat;
import cn.anlper.train.enums.SeatColEnum;
import cn.anlper.train.mapper.TrainSeatMapper;
import cn.anlper.train.req.TrainSeatQueryReq;
import cn.anlper.train.req.TrainSeatSaveReq;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.TrainSeatQueryResp;
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

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TrainSeatService {
    @Resource
    private TrainSeatMapper trainSeatMapper;
    @Resource
    private TrainCarriageService trainCarriageService;

    @Resource
    private SnowFlake snowFlake;
    public void save(TrainSeatSaveReq req) {
        DateTime now = DateTime.now();
        TrainSeat trainSeat = BeanUtil.copyProperties(req, TrainSeat.class);
        if (ObjUtil.isNull(trainSeat.getId())) {
            trainSeat.setId(snowFlake.nextId());
            trainSeat.setCreateTime(now);
            trainSeat.setUpdateTime(now);
            trainSeatMapper.insert(trainSeat);
        } else {
            trainSeat.setUpdateTime(now);
            trainSeatMapper.updateByPrimaryKey(trainSeat);
        }

    }

    public PageResp queryList(TrainSeatQueryReq req) {
        Example example = new Example(TrainSeat.class);
        example.setOrderByClause("train_code asc, carriage_index asc, carriage_seat_index asc");
        Example.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andEqualTo("trainCode", req.getTrainCode());
        }
        PageHelper.startPage(req.getPage(), req.getSize());

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        List<TrainSeat> trainSeatList = trainSeatMapper.selectByExample(example);
        PageInfo<TrainSeat> pageInfo = new PageInfo<>(trainSeatList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<TrainSeatQueryResp> trainSeatQueryRespList = BeanUtil.copyToList(trainSeatList, TrainSeatQueryResp.class);
        PageResp<TrainSeatQueryResp> pageResp = new PageResp<>(trainSeatQueryRespList, pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        trainSeatMapper.deleteByPrimaryKey(id);
    }

    public void genTrainSeat(String trainCode) {
        Date date = new Date();
        // 清空当前车次所有记录
        Example example = new Example(TrainSeat.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("trainCode", trainCode);
        trainSeatMapper.deleteByExample(example );
        // 查找当前车次下的所有车厢
        List<TrainCarriage> trainCarriages = trainCarriageService.selectByTrainCode(trainCode);
        // 循环生成每个车厢的座位
        for (TrainCarriage carriage : trainCarriages) {
            Integer rowCount = carriage.getRowCount();
            String seatType = carriage.getSeatType();
            List<SeatColEnum> colEnumList = SeatColEnum.getColsByType(seatType);
            int seatIndex = 1;
            for (int row = 1; row <= rowCount; row++) {
                for (SeatColEnum colEnum : colEnumList) {
                    TrainSeat trainSeat = new TrainSeat();
                    trainSeat.setId(snowFlake.nextId());
                    trainSeat.setCarriageIndex(carriage.getIndexes());
                    trainSeat.setSeatType(seatType);
                    trainSeat.setTrainCode(trainCode);
                    trainSeat.setSeatCol(colEnum.getCode());
                    trainSeat.setSeatRow(StrUtil.fillBefore(String.valueOf(row), '0', 2));
                    trainSeat.setCarriageSeatIndex(seatIndex++);
                    trainSeat.setCreateTime(date);
                    trainSeat.setUpdateTime(date);
                    trainSeatMapper.insert(trainSeat);
                }
            }
        }
    }

    public List<TrainSeat> selectSeatByTrainCode(String trainCode) {
        Example example = new Example(TrainSeat.class);
        example.createCriteria().andEqualTo("trainCode", trainCode);
        return trainSeatMapper.selectByExample(example);
    }
}
