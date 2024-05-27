package cn.anlper.train.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 表名：daily_train_carriage
 * 表注释：每日车厢
*/
@Table(name = "daily_train_carriage")
public class DailyTrainCarriage {
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
    private Integer indexes;

    /**
     * 座位类型|枚举[SeatTypeEnum]
     */
    @Column(name = "seat_type")
    private String seatType;

    /**
     * 座位数
     */
    @Column(name = "seat_count")
    private Integer seatCount;

    /**
     * 排数
     */
    @Column(name = "row_count")
    private Integer rowCount;

    /**
     * 列数
     */
    @Column(name = "col_count")
    private Integer colCount;

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
     * @return indexes - 箱序
     */
    public Integer getIndexes() {
        return indexes;
    }

    /**
     * 设置箱序
     *
     * @param indexes 箱序
     */
    public void setIndexes(Integer indexes) {
        this.indexes = indexes;
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
     * 获取座位数
     *
     * @return seatCount - 座位数
     */
    public Integer getSeatCount() {
        return seatCount;
    }

    /**
     * 设置座位数
     *
     * @param seatCount 座位数
     */
    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    /**
     * 获取排数
     *
     * @return rowCount - 排数
     */
    public Integer getRowCount() {
        return rowCount;
    }

    /**
     * 设置排数
     *
     * @param rowCount 排数
     */
    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * 获取列数
     *
     * @return colCount - 列数
     */
    public Integer getColCount() {
        return colCount;
    }

    /**
     * 设置列数
     *
     * @param colCount 列数
     */
    public void setColCount(Integer colCount) {
        this.colCount = colCount;
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