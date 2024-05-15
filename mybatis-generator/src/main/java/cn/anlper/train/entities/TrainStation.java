package cn.anlper.train.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * 表名：train_station
 * 表注释：火车车站
*/
@Table(name = "train_station")
public class TrainStation {
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
     * 站序
     */
    private Integer indexes;

    /**
     * 站名
     */
    private String name;

    /**
     * 站名拼音
     */
    @Column(name = "name_pinyin")
    private String namePinyin;

    /**
     * 进站时间
     */
    @Column(name = "in_time")
    private Date inTime;

    /**
     * 出站时间
     */
    @Column(name = "out_time")
    private Date outTime;

    /**
     * 停站时长
     */
    @Column(name = "stop_time")
    private Date stopTime;

    /**
     * 里程（公里）|从上一站到本站的距离
     */
    private BigDecimal km;

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
     * 获取站序
     *
     * @return indexes - 站序
     */
    public Integer getIndexes() {
        return indexes;
    }

    /**
     * 设置站序
     *
     * @param indexes 站序
     */
    public void setIndexes(Integer indexes) {
        this.indexes = indexes;
    }

    /**
     * 获取站名
     *
     * @return name - 站名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置站名
     *
     * @param name 站名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取站名拼音
     *
     * @return namePinyin - 站名拼音
     */
    public String getNamePinyin() {
        return namePinyin;
    }

    /**
     * 设置站名拼音
     *
     * @param namePinyin 站名拼音
     */
    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    /**
     * 获取进站时间
     *
     * @return inTime - 进站时间
     */
    public Date getInTime() {
        return inTime;
    }

    /**
     * 设置进站时间
     *
     * @param inTime 进站时间
     */
    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    /**
     * 获取出站时间
     *
     * @return outTime - 出站时间
     */
    public Date getOutTime() {
        return outTime;
    }

    /**
     * 设置出站时间
     *
     * @param outTime 出站时间
     */
    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    /**
     * 获取停站时长
     *
     * @return stopTime - 停站时长
     */
    public Date getStopTime() {
        return stopTime;
    }

    /**
     * 设置停站时长
     *
     * @param stopTime 停站时长
     */
    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    /**
     * 获取里程（公里）|从上一站到本站的距离
     *
     * @return km - 里程（公里）|从上一站到本站的距离
     */
    public BigDecimal getKm() {
        return km;
    }

    /**
     * 设置里程（公里）|从上一站到本站的距离
     *
     * @param km 里程（公里）|从上一站到本站的距离
     */
    public void setKm(BigDecimal km) {
        this.km = km;
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