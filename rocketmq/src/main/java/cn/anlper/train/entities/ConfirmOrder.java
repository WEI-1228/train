package cn.anlper.train.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 表名：confirm_order
 * 表注释：确认订单
*/
@Table(name = "confirm_order")
public class ConfirmOrder {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 会员id
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 日期
     */
    private Date date;

    /**
     * 车次编号
     */
    @Column(name = "train_code")
    private String trainCode;

    /**
     * 出发站
     */
    private String start;

    /**
     * 到达站
     */
    private String end;

    /**
     * 余票ID
     */
    @Column(name = "daily_train_ticket_id")
    private Long dailyTrainTicketId;

    /**
     * 订单状态|枚举[ConfirmOrderStatusEnum]
     */
    private String status;

    /**
     * 新增时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 车票
     */
    private String tickets;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取会员id
     *
     * @return memberId - 会员id
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置会员id
     *
     * @param memberId 会员id
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取日期
     *
     * @return date - 日期
     */
    public Date getDate() {
        return date;
    }

    /**
     * 设置日期
     *
     * @param date 日期
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 获取车次编号
     *
     * @return trainCode - 车次编号
     */
    public String getTrainCode() {
        return trainCode;
    }

    /**
     * 设置车次编号
     *
     * @param trainCode 车次编号
     */
    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    /**
     * 获取出发站
     *
     * @return start - 出发站
     */
    public String getStart() {
        return start;
    }

    /**
     * 设置出发站
     *
     * @param start 出发站
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * 获取到达站
     *
     * @return end - 到达站
     */
    public String getEnd() {
        return end;
    }

    /**
     * 设置到达站
     *
     * @param end 到达站
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * 获取余票ID
     *
     * @return dailyTrainTicketId - 余票ID
     */
    public Long getDailyTrainTicketId() {
        return dailyTrainTicketId;
    }

    /**
     * 设置余票ID
     *
     * @param dailyTrainTicketId 余票ID
     */
    public void setDailyTrainTicketId(Long dailyTrainTicketId) {
        this.dailyTrainTicketId = dailyTrainTicketId;
    }

    /**
     * 获取订单状态|枚举[ConfirmOrderStatusEnum]
     *
     * @return status - 订单状态|枚举[ConfirmOrderStatusEnum]
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置订单状态|枚举[ConfirmOrderStatusEnum]
     *
     * @param status 订单状态|枚举[ConfirmOrderStatusEnum]
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取新增时间
     *
     * @return createTime - 新增时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置新增时间
     *
     * @param createTime 新增时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return updateTime - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取车票
     *
     * @return tickets - 车票
     */
    public String getTickets() {
        return tickets;
    }

    /**
     * 设置车票
     *
     * @param tickets 车票
     */
    public void setTickets(String tickets) {
        this.tickets = tickets;
    }
}