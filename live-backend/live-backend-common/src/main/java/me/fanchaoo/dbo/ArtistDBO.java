package me.fanchaoo.dbo;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * 
 *
 * @author null
 * @date 2020-05-01
 */
@Data
public class ArtistDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 微信小程序appId
     */
    private String mpWeixinAppId;

    /**
     * 微信小程序appSecret
     */
    private String mpWeixinAppSecret;

    /**
     * QQ小程序appId
     */
    private String mpQqAppId;

    /**
     * QQ小程序appSecret
     */
    private String mpQqAppSecret;

    /**
     * 
     */
    private String randomKey;

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

    public String getMpWeixinAppId() {
        return mpWeixinAppId;
    }

    public void setMpWeixinAppId(String mpWeixinAppId) {
        this.mpWeixinAppId = mpWeixinAppId == null ? null : mpWeixinAppId.trim();
    }

    public String getMpWeixinAppSecret() {
        return mpWeixinAppSecret;
    }

    public void setMpWeixinAppSecret(String mpWeixinAppSecret) {
        this.mpWeixinAppSecret = mpWeixinAppSecret == null ? null : mpWeixinAppSecret.trim();
    }

    public String getMpQqAppId() {
        return mpQqAppId;
    }

    public void setMpQqAppId(String mpQqAppId) {
        this.mpQqAppId = mpQqAppId == null ? null : mpQqAppId.trim();
    }

    public String getMpQqAppSecret() {
        return mpQqAppSecret;
    }

    public void setMpQqAppSecret(String mpQqAppSecret) {
        this.mpQqAppSecret = mpQqAppSecret == null ? null : mpQqAppSecret.trim();
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey == null ? null : randomKey.trim();
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