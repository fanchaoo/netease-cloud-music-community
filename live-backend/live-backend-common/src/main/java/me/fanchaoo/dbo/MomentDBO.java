package me.fanchaoo.dbo;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * @author null
 * @date 2019-12-31
 */
@Data
public class MomentDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 发送者id
     */
    private Long userId;

    /**
     * 类型（TEXT：纯文本，IMAGE：图片，VIDEO：视频）
     */
    private String type;

    /**
     * 文本内容
     */
    private String textContent;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 点赞数量
     */
    private Integer likeCount;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 转发数量
     */
    private Integer repostCount;

    /**
     * 分享数量
     */
    private Integer shareCount;


    /**
     * 浏览数量
     */
    private Integer viewCount;

    /**
     * 转发的动态id
     */
    private Long repostMomentId;

    /**
     * 状态（0：已屏蔽，1：正常，2：已删除）
     */
    private Integer status;

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