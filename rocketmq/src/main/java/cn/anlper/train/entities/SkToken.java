package cn.anlper.train.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 表名：sk_token
 * 表注释：秒杀令牌
*/
@Table(name = "sk_token")
public class SkToken {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

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
     * 令牌余量
     */
    private Integer count;

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
     * 获取令牌余量
     *
     * @return count - 令牌余量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置令牌余量
     *
     * @param count 令牌余量
     */
    public void setCount(Integer count) {
        this.count = count;
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