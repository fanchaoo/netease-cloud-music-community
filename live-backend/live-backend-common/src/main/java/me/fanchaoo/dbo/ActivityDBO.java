package me.fanchaoo.dbo;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * 
 *
 * @author null
 * @date 2020-05-07
 */
@Data
public class ActivityDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 演出名称
     */
    private String name;

    /**
     * 演出起始时间
     */
    private DateTime showStartTime;

    /**
     * 演出结束时间
     */
    private DateTime showEndTime;

    /**
     * 场地城市
     */
    private String venueCity;

    /**
     * 
     */
    private String venueName;

    /**
     * 
     */
    private String venueAddress;

    /**
     * 海报图片
     */
    private String posterImage;

    /**
     * 数据来源渠道（秀动：SHOWSTART）
     */
    private String sourceChannel;

    /**
     * 数据来源渠道-对象id
     */
    private String sourceId;

    /**
     * 来源url
     */
    private String sourceUrl;

    /**
     * 线上活动
     */
    private Integer online;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public DateTime getShowStartTime() {
        return showStartTime;
    }

    public void setShowStartTime(DateTime showStartTime) {
        this.showStartTime = showStartTime;
    }

    public DateTime getShowEndTime() {
        return showEndTime;
    }

    public void setShowEndTime(DateTime showEndTime) {
        this.showEndTime = showEndTime;
    }

    public String getVenueCity() {
        return venueCity;
    }

    public void setVenueCity(String venueCity) {
        this.venueCity = venueCity == null ? null : venueCity.trim();
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName == null ? null : venueName.trim();
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress == null ? null : venueAddress.trim();
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage == null ? null : posterImage.trim();
    }

    public String getSourceChannel() {
        return sourceChannel;
    }

    public void setSourceChannel(String sourceChannel) {
        this.sourceChannel = sourceChannel == null ? null : sourceChannel.trim();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl == null ? null : sourceUrl.trim();
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
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