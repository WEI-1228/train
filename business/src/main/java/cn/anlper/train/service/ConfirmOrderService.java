package cn.anlper.train.service;

import cn.anlper.train.entities.ConfirmOrder;
import cn.anlper.train.mapper.ConfirmOrderMapper;
import cn.anlper.train.req.ConfirmOrderQueryReq;
import cn.anlper.train.req.ConfirmOrderSaveReq;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.ConfirmOrderQueryResp;
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
public class ConfirmOrderService {
    @Resource
    private ConfirmOrderMapper confirmOrderMapper;

    @Resource
    private SnowFlake snowFlake;
    public void save(ConfirmOrderSaveReq req) {
        DateTime now = DateTime.now();
        ConfirmOrder confirmOrder = BeanUtil.copyProperties(req, ConfirmOrder.class);
        if (ObjUtil.isNull(confirmOrder.getId())) {
            confirmOrder.setId(snowFlake.nextId());
            confirmOrder.setCreateTime(now);
            confirmOrder.setUpdateTime(now);
            confirmOrderMapper.insert(confirmOrder);
        } else {
            confirmOrder.setUpdateTime(now);
            confirmOrderMapper.updateByPrimaryKey(confirmOrder);
        }

    }

    public PageResp queryList(ConfirmOrderQueryReq req) {
        Example example = new Example(ConfirmOrder.class);
        PageHelper.startPage(req.getPage(), req.getSize());

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        List<ConfirmOrder> confirmOrderList = confirmOrderMapper.selectByExample(example);
        PageInfo<ConfirmOrder> pageInfo = new PageInfo<>(confirmOrderList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<ConfirmOrderQueryResp> confirmOrderQueryRespList = BeanUtil.copyToList(confirmOrderList, ConfirmOrderQueryResp.class);
        PageResp<ConfirmOrderQueryResp> pageResp = new PageResp<>(confirmOrderQueryRespList, pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        confirmOrderMapper.deleteByPrimaryKey(id);
    }
}
