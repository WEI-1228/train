package cn.anlper.train.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 表名：daily_train_seat
 * 表注释：每日座位
*/
@Table(name = "daily_train_seat")
public class DailyTrainSeat {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 日期
     */
    @Column(name = "daily_date")
    private Date dailyDate;

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
    @Column(name = "daily_row")
    private String dailyRow;

    /**
     * 列号|枚举[SeatColEnum]
     */
    @Column(name = "daily_col")
    private String dailyCol;

    /**
     * 座位类型|枚举[SeatTypeEnum]
     */
    @Column(name = "seat_type")
    private String seatType;

    /**
     * 同车箱座序
     */
    @Column(name = "carriage_seat_index")
    private Integer carriageSeatIndex;

    /**
     * 售卖情况|将经过的车站用01拼接，0表示可卖，1表示已卖
     */
    private String sell;

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
     * 获取日期
     *
     * @return dailyDate - 日期
     */
    public Date getDailyDate() {
        return dailyDate;
    }

    /**
     * 设置日期
     *
     * @param dailyDate 日期
     */
    public void setDailyDate(Date dailyDate) {
        this.dailyDate = dailyDate;
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
     * @return dailyRow - 排号|01, 02
     */
    public String getDailyRow() {
        return dailyRow;
    }

    /**
     * 设置排号|01, 02
     *
     * @param dailyRow 排号|01, 02
     */
    public void setDailyRow(String dailyRow) {
        this.dailyRow = dailyRow;
    }

    /**
     * 获取列号|枚举[SeatColEnum]
     *
     * @return dailyCol - 列号|枚举[SeatColEnum]
     */
    public String getDailyCol() {
        return dailyCol;
    }

    /**
     * 设置列号|枚举[SeatColEnum]
     *
     * @param dailyCol 列号|枚举[SeatColEnum]
     */
    public void setDailyCol(String dailyCol) {
        this.dailyCol = dailyCol;
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
     * 获取同车箱座序
     *
     * @return carriageSeatIndex - 同车箱座序
     */
    public Integer getCarriageSeatIndex() {
        return carriageSeatIndex;
    }

    /**
     * 设置同车箱座序
     *
     * @param carriageSeatIndex 同车箱座序
     */
    public void setCarriageSeatIndex(Integer carriageSeatIndex) {
        this.carriageSeatIndex = carriageSeatIndex;
    }

    /**
     * 获取售卖情况|将经过的车站用01拼接，0表示可卖，1表示已卖
     *
     * @return sell - 售卖情况|将经过的车站用01拼接，0表示可卖，1表示已卖
     */
    public String getSell() {
        return sell;
    }

    /**
     * 设置售卖情况|将经过的车站用01拼接，0表示可卖，1表示已卖
     *
     * @param sell 售卖情况|将经过的车站用01拼接，0表示可卖，1表示已卖
     */
    public void setSell(String sell) {
        this.sell = sell;
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