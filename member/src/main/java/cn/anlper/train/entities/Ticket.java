package cn.anlper.train.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 表名：ticket
 * 表注释：车票
*/
@Table(name = "ticket")
public class Ticket {
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
     * 乘客id
     */
    @Column(name = "passenger_id")
    private Long passengerId;

    /**
     * 乘客姓名
     */
    @Column(name = "passenger_name")
    private String passengerName;

    /**
     * 日期
     */
    @Column(name = "train_date")
    private Date trainDate;

    /**
     * 车次编号
     */
    @Column(name = "train_code")
    private String trainCode;

    /**
     * 箱序
     */
    @Column(name = "carriage_index")
    private Integer carriageIndex;

    /**
     * 排号|01, 02
     */
    @Column(name = "seat_row")
    private String seatRow;

    /**
     * 列号|枚举[SeatColEnum]
     */
    @Column(name = "seat_col")
    private String seatCol;

    /**
     * 出发站
     */
    @Column(name = "start_station")
    private String startStation;

    /**
     * 出发时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 到达站
     */
    @Column(name = "end_station")
    private String endStation;

    /**
     * 到站时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 座位类型|枚举[SeatTypeEnum]
     */
    @Column(name = "seat_type")
    private String seatType;

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
     * 获取乘客id
     *
     * @return passengerId - 乘客id
     */
    public Long getPassengerId() {
        return passengerId;
    }

    /**
     * 设置乘客id
     *
     * @param passengerId 乘客id
     */
    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    /**
     * 获取乘客姓名
     *
     * @return passengerName - 乘客姓名
     */
    public String getPassengerName() {
        return passengerName;
    }

    /**
     * 设置乘客姓名
     *
     * @param passengerName 乘客姓名
     */
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    /**
     * 获取日期
     *
     * @return trainDate - 日期
     */
    public Date getTrainDate() {
        return trainDate;
    }

    /**
     * 设置日期
     *
     * @param trainDate 日期
     */
    public void setTrainDate(Date trainDate) {
        this.trainDate = trainDate;
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
     * 获取箱序
     *
     * @return carriageIndex - 箱序
     */
    public Integer getCarriageIndex() {
        return carriageIndex;
    }

    /**
     * 设置箱序
     *
     * @param carriageIndex 箱序
     */
    public void setCarriageIndex(Integer carriageIndex) {
        this.carriageIndex = carriageIndex;
    }

    /**
     * 获取排号|01, 02
     *
     * @return seatRow - 排号|01, 02
     */
    public String getSeatRow() {
        return seatRow;
    }

    /**
     * 设置排号|01, 02
     *
     * @param seatRow 排号|01, 02
     */
    public void setSeatRow(String seatRow) {
        this.seatRow = seatRow;
    }

    /**
     * 获取列号|枚举[SeatColEnum]
     *
     * @return seatCol - 列号|枚举[SeatColEnum]
     */
    public String getSeatCol() {
        return seatCol;
    }

    /**
     * 设置列号|枚举[SeatColEnum]
     *
     * @param seatCol 列号|枚举[SeatColEnum]
     */
    public void setSeatCol(String seatCol) {
        this.seatCol = seatCol;
    }

    /**
     * 获取出发站
     *
     * @return startStation - 出发站
     */
    public String getStartStation() {
        return startStation;
    }

    /**
     * 设置出发站
     *
     * @param startStation 出发站
     */
    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    /**
     * 获取出发时间
     *
     * @return startTime - 出发时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置出发时间
     *
     * @param startTime 出发时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取到达站
     *
     * @return endStation - 到达站
     */
    public String getEndStation() {
        return endStation;
    }

    /**
     * 设置到达站
     *
     * @param endStation 到达站
     */
    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    /**
     * 获取到站时间
     *
     * @return endTime - 到站时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置到站时间
     *
     * @param endTime 到站时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取座位类型|枚举[SeatTypeEnum]
     *
     * @return seatType - 座位类型|枚举[SeatTypeEnum]
     */
    public String getSeatType() {
        return seatType;
    }

    /**
     * 设置座位类型|枚举[SeatTypeEnum]
     *
     * @param seatType 座位类型|枚举[SeatTypeEnum]
     */
    public void setSeatType(String seatType) {
        this.seatType = seatType;
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
}