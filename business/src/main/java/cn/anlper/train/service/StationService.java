package cn.anlper.train.service;

import cn.anlper.train.entities.Station;
import cn.anlper.train.exception.BusinessException;
import cn.anlper.train.exception.BusinessExceptionEnum;
import cn.anlper.train.mapper.StationMapper;
import cn.anlper.train.req.StationQueryReq;
import cn.anlper.train.req.StationSaveReq;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.StationQueryResp;
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
public class StationService {
    @Resource
    private StationMapper stationMapper;

    @Resource
    private SnowFlake snowFlake;
    public void save(StationSaveReq req) {
        DateTime now = DateTime.now();
        Station station = BeanUtil.copyProperties(req, Station.class);
        if (ObjUtil.isNull(station.getId())) {
            Station stationDB = stationMapper.selectOne(station);
            if (ObjUtil.isNotNull(stationDB))
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_STATION_NAME_UNIQUE_ERROR);

            station.setId(snowFlake.nextId());
            station.setCreateTime(now);
            station.setUpdateTime(now);
            stationMapper.insert(station);
        } else {
            station.setUpdateTime(now);
            stationMapper.updateByPrimaryKey(station);
        }

    }

    public PageResp queryList(StationQueryReq req) {
        Example example = new Example(Station.class);

        PageHelper.startPage(req.getPage(), req.getSize());

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        List<Station> stationList = stationMapper.selectByExample(example);
        PageInfo<Station> pageInfo = new PageInfo<>(stationList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<StationQueryResp> stationQueryRespList = BeanUtil.copyToList(stationList, StationQueryResp.class);
        PageResp<StationQueryResp> pageResp = new PageResp<>(stationQueryRespList, pageInfo.getTotal());
        return pageResp;
    }

    public List<StationQueryResp> queryAll() {
        Example example = new Example(Station.class);
        List<Station> stationList = stationMapper.selectByExample(example);
        return BeanUtil.copyToList(stationList, StationQueryResp.class);
    }


    public void delete(Long id) {
        stationMapper.deleteByPrimaryKey(id);
    }
}
