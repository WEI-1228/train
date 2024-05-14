package cn.anlper.train.service;

import cn.anlper.train.context.LoginMemberContext;
import cn.anlper.train.entities.Passenger;
import cn.anlper.train.mapper.PassengerMapper;
import cn.anlper.train.req.PassengerQueryReq;
import cn.anlper.train.req.PassengerSaveReq;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.PassengerQueryResp;
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
public class PassengerService {
    @Resource
    private PassengerMapper passengerMapper;

    @Resource
    private SnowFlake snowFlake;
    public void save(PassengerSaveReq req) {
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        if (ObjUtil.isNull(passenger.getId())) {
            passenger.setMemberId(LoginMemberContext.getId());
            passenger.setId(snowFlake.nextId());
            passenger.setCreateTime(now);
            passenger.setUpdateTime(now);
            passengerMapper.insert(passenger);
        } else {
            passenger.setUpdateTime(now);
            passengerMapper.updateByPrimaryKey(passenger);
        }

    }

    public PageResp queryList(PassengerQueryReq req) {
        Example example = new Example(Passenger.class);
        Example.Criteria criteria = example.createCriteria();
        if (ObjUtil.isNotNull(req.getMemberId()))
            criteria.andEqualTo("memberId", req.getMemberId());
        PageHelper.startPage(req.getPage(), req.getSize());

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        List<Passenger> passengerList = passengerMapper.selectByExample(example);
        PageInfo<Passenger> pageInfo = new PageInfo<>(passengerList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<PassengerQueryResp> passengerQueryRespList = BeanUtil.copyToList(passengerList, PassengerQueryResp.class);
        PageResp<PassengerQueryResp> pageResp = new PageResp<>(passengerQueryRespList, pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        passengerMapper.deleteByPrimaryKey(id);
    }
}
