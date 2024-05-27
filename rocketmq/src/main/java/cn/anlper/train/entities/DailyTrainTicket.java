package cn.anlper.train.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 表名：daily_train_ticket
 * 表注释：余票信息
*/
@Table(name = "daily_train_ticket")
public class DailyTrainTicket {
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
     * 出发站
     */
    private String start;

    /**
     * 出发站拼音
     */
    @Column(name = "start_pinyin")
    private String startPinyin;

    /**
     * 出发时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 出发站序|本站是整个车次的第几站
     */
    @Column(name = "start_index")
    private Integer startIndex;

    /**
     * 到达站
     */
    private String end;

    /**
     * 到达站拼音
     */
    @Column(name = "end_pinyin")
    private String endPinyin;

    /**
     * 到站时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 到站站序|本站是整个车次的第几站
     */
    @Column(name = "end_index")
    private Integer endIndex;

    /**
     * 一等座余票
     */
    private Integer ydz;

    /**
     * 一等座票价
     */
    @Column(name = "ydz_price")
    private BigDecimal ydzPrice;

    /**
     * 二等座余票
     */
    private Integer edz;

    /**
     * 二等座票价
     */
    @Column(name = "edz_price")
    private BigDecimal edzPrice;

    /**
     * 软卧余票
     */
    private Integer rw;

    /**
     * 软卧票价
     */
    @Column(name = "rw_price")
    private BigDecimal rwPrice;

    /**
     * 硬卧余票
     */
    private Integer yw;

    /**
     * 硬卧票价
     */
    @Column(name = "yw_price")
    private BigDecimal ywPrice;

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
     * 获取出发站拼音
     *
     * @return startPinyin - 出发站拼音
     */
    public String getStartPinyin() {
        return startPinyin;
    }

    /**
     * 设置出发站拼音
     *
     * @param startPinyin 出发站拼音
     */
    public void setStartPinyin(String startPinyin) {
        this.startPinyin = startPinyin;
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
     * 获取出发站序|本站是整个车次的第几站
     *
     * @return startIndex - 出发站序|本站是整个车次的第几站
     */
    public Integer getStartIndex() {
        return startIndex;
    }

    /**
     * 设置出发站序|本站是整个车次的第几站
     *
     * @param startIndex 出发站序|本站是整个车次的第几站
     */
    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
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
     * 获取到达站拼音
     *
     * @return endPinyin - 到达站拼音
     */
    public String getEndPinyin() {
        return endPinyin;
    }

    /**
     * 设置到达站拼音
     *
     * @param endPinyin 到达站拼音
     */
    public void setEndPinyin(String endPinyin) {
        this.endPinyin = endPinyin;
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
     * 获取到站站序|本站是整个车次的第几站
     *
     * @return endIndex - 到站站序|本站是整个车次的第几站
     */
    public Integer getEndIndex() {
        return endIndex;
    }

    /**
     * 设置到站站序|本站是整个车次的第几站
     *
     * @param endIndex 到站站序|本站是整个车次的第几站
     */
    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    /**
     * 获取一等座余票
     *
     * @return ydz - 一等座余票
     */
    public Integer getYdz() {
        return ydz;
    }

    /**
     * 设置一等座余票
     *
     * @param ydz 一等座余票
     */
    public void setYdz(Integer ydz) {
        this.ydz = ydz;
    }

    /**
     * 获取一等座票价
     *
     * @return ydzPrice - 一等座票价
     */
    public BigDecimal getYdzPrice() {
        return ydzPrice;
    }

    /**
     * 设置一等座票价
     *
     * @param ydzPrice 一等座票价
     */
    public void setYdzPrice(BigDecimal ydzPrice) {
        this.ydzPrice = ydzPrice;
    }

    /**
     * 获取二等座余票
     *
     * @return edz - 二等座余票
     */
    public Integer getEdz() {
        return edz;
    }

    /**
     * 设置二等座余票
     *
     * @param edz 二等座余票
     */
    public void setEdz(Integer edz) {
        this.edz = edz;
    }

    /**
     * 获取二等座票价
     *
     * @return edzPrice - 二等座票价
     */
    public BigDecimal getEdzPrice() {
        return edzPrice;
    }

    /**
     * 设置二等座票价
     *
     * @param edzPrice 二等座票价
     */
    public void setEdzPrice(BigDecimal edzPrice) {
        this.edzPrice = edzPrice;
    }

    /**
     * 获取软卧余票
     *
     * @return rw - 软卧余票
     */
    public Integer getRw() {
        return rw;
    }

    /**
     * 设置软卧余票
     *
     * @param rw 软卧余票
     */
    public void setRw(Integer rw) {
        this.rw = rw;
    }

    /**
     * 获取软卧票价
     *
     * @return rwPrice - 软卧票价
     */
    public BigDecimal getRwPrice() {
        return rwPrice;
    }

    /**
     * 设置软卧票价
     *
     * @param rwPrice 软卧票价
     */
    public void setRwPrice(BigDecimal rwPrice) {
        this.rwPrice = rwPrice;
    }

    /**
     * 获取硬卧余票
     *
     * @return yw - 硬卧余票
     */
    public Integer getYw() {
        return yw;
    }

    /**
     * 设置硬卧余票
     *
     * @param yw 硬卧余票
     */
    public void setYw(Integer yw) {
        this.yw = yw;
    }

    /**
     * 获取硬卧票价
     *
     * @return ywPrice - 硬卧票价
     */
    public BigDecimal getYwPrice() {
        return ywPrice;
    }

    /**
     * 设置硬卧票价
     *
     * @param ywPrice 硬卧票价
     */
    public void setYwPrice(BigDecimal ywPrice) {
        this.ywPrice = ywPrice;
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