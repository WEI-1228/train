package cn.anlper.train.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 表名：train_seat
 * 表注释：座位
*/
@Table(name = "train_seat")
public class TrainSeat {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 车次编号
     */
    @Column(name = "train_code")
    private String trainCode;

    /**
     * 厢序
     */
    @Column(name = "carriage_index")
    private Integer carriageIndex;

    /**
     * 排号|01, 02
     */
    private String row;

    /**
     * 列号|枚举[SeatColEnum]
     */
    private String col;

    /**
     * 座位类型|枚举[SeatTypeEnum]
     */
    @Column(name = "seat_type")
    private String seatType;

    /**
     * 同车厢座序
     */
    @Column(name = "carriage_seat_index")
    private Integer carriageSeatIndex;

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
     * 获取厢序
     *
     * @return carriageIndex - 厢序
     */
    public Integer getCarriageIndex() {
        return carriageIndex;
    }

    /**
     * 设置厢序
     *
     * @param carriageIndex 厢序
     */
    public void setCarriageIndex(Integer carriageIndex) {
        this.carriageIndex = carriageIndex;
    }

    /**
     * 获取排号|01, 02
     *
     * @return row - 排号|01, 02
     */
    public String getRow() {
        return row;
    }

    /**
     * 设置排号|01, 02
     *
     * @param row 排号|01, 02
     */
    public void setRow(String row) {
        this.row = row;
    }

    /**
     * 获取列号|枚举[SeatColEnum]
     *
     * @return col - 列号|枚举[SeatColEnum]
     */
    public String getCol() {
        return col;
    }

    /**
     * 设置列号|枚举[SeatColEnum]
     *
     * @param col 列号|枚举[SeatColEnum]
     */
    public void setCol(String col) {
        this.col = col;
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
     * 获取同车厢座序
     *
     * @return carriageSeatIndex - 同车厢座序
     */
    public Integer getCarriageSeatIndex() {
        return carriageSeatIndex;
    }

    /**
     * 设置同车厢座序
     *
     * @param carriageSeatIndex 同车厢座序
     */
    public void setCarriageSeatIndex(Integer carriageSeatIndex) {
        this.carriageSeatIndex = carriageSeatIndex;
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