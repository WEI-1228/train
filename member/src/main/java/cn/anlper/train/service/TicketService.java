package cn.anlper.train.service;

import cn.anlper.train.entities.Ticket;
import cn.anlper.train.mapper.TicketMapper;
import cn.anlper.train.req.MemberTicketSaveReq;
import cn.anlper.train.req.TicketQueryReq;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.TicketQueryResp;
import cn.anlper.train.utils.SnowFlake;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Slf4j
public class TicketService {
    @Resource
    private TicketMapper ticketMapper;

    @Resource
    private SnowFlake snowFlake;
    public void save(MemberTicketSaveReq req) {
        DateTime now = DateTime.now();
        Ticket ticket = BeanUtil.copyProperties(req, Ticket.class);
        ticket.setId(snowFlake.nextId());
        ticket.setCreateTime(now);
        ticket.setUpdateTime(now);
        ticketMapper.insert(ticket);
    }

    public PageResp queryList(TicketQueryReq req) {
        Example example = new Example(Ticket.class);
        PageHelper.startPage(req.getPage(), req.getSize());

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        List<Ticket> ticketList = ticketMapper.selectByExample(example);
        PageInfo<Ticket> pageInfo = new PageInfo<>(ticketList);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<TicketQueryResp> ticketQueryRespList = BeanUtil.copyToList(ticketList, TicketQueryResp.class);
        PageResp<TicketQueryResp> pageResp = new PageResp<>(ticketQueryRespList, pageInfo.getTotal());
        return pageResp;
    }

}
