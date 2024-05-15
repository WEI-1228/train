package cn.anlper.train.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 表名：station
 * 表注释：车站
*/
@Table(name = "station")
public class Station {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

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
     * 站名拼音首字母
     */
    @Column(name = "name_py")
    private String namePy;

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
     * 获取站名拼音首字母
     *
     * @return namePy - 站名拼音首字母
     */
    public String getNamePy() {
        return namePy;
    }

    /**
     * 设置站名拼音首字母
     *
     * @param namePy 站名拼音首字母
     */
    public void setNamePy(String namePy) {
        this.namePy = namePy;
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