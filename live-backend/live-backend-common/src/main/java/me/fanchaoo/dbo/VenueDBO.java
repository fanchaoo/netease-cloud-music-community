package me.fanchaoo.dbo;

import lombok.Data;
import org.joda.time.DateTime;

/**
 *
 *
 * @author null
 * @date 2019-12-27
 */
@Data
public class VenueDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 场地名称
     */
    private String name;

    /**
     * 内容
     */
    private String address;

    /**
     * 创建时间
     */
    private DateTime createTime;

    /**
     * 更新时间
     */
    private DateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public DateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(DateTime updateTime) {
        this.updateTime = updateTime;
    }
}