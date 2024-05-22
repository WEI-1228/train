package cn.anlper.train.service;

import cn.anlper.train.context.LoginMemberContext;
import cn.anlper.train.entities.ConfirmOrder;
import cn.anlper.train.enums.ConfirmOrderStatusEnum;
import cn.anlper.train.mapper.ConfirmOrderMapper;
import cn.anlper.train.req.ConfirmOrderDoReq;
import cn.anlper.train.utils.SnowFlake;
import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ConfirmOrderService {
    @Resource
    private ConfirmOrderMapper confirmOrderMapper;

    @Resource
    private SnowFlake snowFlake;
    public void doConfirm(ConfirmOrderDoReq req) {
        // 业务校验，比如车次是否存在，车次是否在有效期等，这里省略

        // 保存确认订单表，订单状态为初始
        Date now = new Date();
        ConfirmOrder confirmOrder = new ConfirmOrder();
        confirmOrder.setId(snowFlake.nextId());
        confirmOrder.setMemberId(LoginMemberContext.getId());
        confirmOrder.setDate(req.getDate());
        confirmOrder.setTrainCode(req.getTrainCode());
        confirmOrder.setStart(req.getStart());
        confirmOrder.setEnd(req.getEnd());
        confirmOrder.setDailyTrainTicketId(req.getDailyTrainTicketId());
        confirmOrder.setStatus(ConfirmOrderStatusEnum.INIT.getCode());
        confirmOrder.setCreateTime(now);
        confirmOrder.setUpdateTime(now);
        confirmOrder.setTickets(JSON.toJSONString(req.getTickets()));
        confirmOrderMapper.insert(confirmOrder);

        // 查出余票记录，需要得到真实库存

        // 扣减余票数量

        // 选座

            // 一个车厢一个车厢的获取座位数据

            // 挑选符合条件的座位

        // 选中的座位做事务处理：

            // 修改座位表售卖情况sell

            // 余票详情表修改余票

            // 为会员增加购票记录

            // 更新确认订单为成功
    }

}
