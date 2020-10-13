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
public class PrivateLetterDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 发送者id
     */
    private Long senderUserId;

    /**
     * 接收者id
     */
    private Long receiverUserId;

    /**
     * 类型（0：纯文本，1：图片）
     */
    private Integer type;

    /**
     * 文本内容
     */
    private String textContent;

    /**
     * 图片链接
     */
    private String imageUrl;

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

    public Long getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }

    public Long getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(Long receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent == null ? null : textContent.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
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