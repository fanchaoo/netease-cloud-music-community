package me.fanchaoo.dbo;

import lombok.Data;

import org.joda.time.DateTime;

/**
 * @author null
 * @date 2020-05-01
 */
@Data
public class UserDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 性别（0：女，1：男）
     */
    private Integer gender;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 个人简介
     */
    private String description;

    /**
     * 微信小程序unionId
     */
    private String miniWeixinUnionId;

    /**
     * 微信小程序openId
     */
    private String miniWeixinOpenId;

    /**
     * QQ小程序unionId
     */
    private String miniQqUnionId;

    /**
     * QQ小程序openId
     */
    private String miniQqOpenId;

    /**
     * 国家
     */
    private String country;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 大学
     */
    private String university;

    /**
     * 关注人数
     */
    private Integer followCount;

    /**
     * 粉丝人数
     */
    private Integer fansCount;

    /**
     * 私信未读
     */
    private Integer letterUnread;

    /**
     * 评论未读
     */
    private Integer commentUnread;

    /**
     * @未读
     */
    private Integer atUnread;

    /**
     * 通知未读
     */
    private Integer notificationUnread;

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
}