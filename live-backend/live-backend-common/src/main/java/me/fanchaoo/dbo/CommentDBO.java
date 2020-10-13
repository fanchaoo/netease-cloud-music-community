package me.fanchaoo.dbo;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * @author null
 * @date 2020-02-28
 */
@Data
public class CommentDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 动态id
     */
    private Long momentId;

    /**
     * 内容
     */
    private String content;

    /**
     * 父评论id
     */
    private Long parentId;

    /**
     * 回复哪个评论
     */
    private Long replyToId;

    /**
     * 点赞数量
     */
    private Integer likeCount;

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