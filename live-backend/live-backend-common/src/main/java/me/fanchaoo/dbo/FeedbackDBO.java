package me.fanchaoo.dbo;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * 
 *
 * @author null
 * @date 2020-06-20
 */
@Data
public class FeedbackDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 类型
     */
    private String type;

    /**
     * 内容
     */
    private String content;

    /**
     * 联系方式
     */
    private String contactInfo;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 演出者id
     */
    private Long artistId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo == null ? null : contactInfo.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
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