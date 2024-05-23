package cn.anlper.train.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 表名：train
 * 表注释：车次
*/
@Table(name = "train")
public class Train {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 车次编号
     */
    private String code;

    /**
     * 车次类型|枚举[TrainTypeEnum]
     */
    private String type;

    /**
     * 始发站
     */
    private String start;

    /**
     * 始发站拼音
     */
    @Column(name = "start_pinyin")
    private String startPinyin;

    /**
     * 出发时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 终点站
     */
    private String end;

    /**
     * 终点站拼音
     */
    @Column(name = "end_pinyin")
    private String endPinyin;

    /**
     * 到站时间
     */
    @Column(name = "end_time")
    private Date endTime;

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
     * @return code - 车次编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置车次编号
     *
     * @param code 车次编号
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取车次类型|枚举[TrainTypeEnum]
     *
     * @return type - 车次类型|枚举[TrainTypeEnum]
     */
    public String getType() {
        return type;
    }

    /**
     * 设置车次类型|枚举[TrainTypeEnum]
     *
     * @param type 车次类型|枚举[TrainTypeEnum]
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取始发站
     *
     * @return start - 始发站
     */
    public String getStart() {
        return start;
    }

    /**
     * 设置始发站
     *
     * @param start 始发站
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * 获取始发站拼音
     *
     * @return startPinyin - 始发站拼音
     */
    public String getStartPinyin() {
        return startPinyin;
    }

    /**
     * 设置始发站拼音
     *
     * @param startPinyin 始发站拼音
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
     * 获取终点站
     *
     * @return end - 终点站
     */
    public String getEnd() {
        return end;
    }

    /**
     * 设置终点站
     *
     * @param end 终点站
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * 获取终点站拼音
     *
     * @return endPinyin - 终点站拼音
     */
    public String getEndPinyin() {
        return endPinyin;
    }

    /**
     * 设置终点站拼音
     *
     * @param endPinyin 终点站拼音
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