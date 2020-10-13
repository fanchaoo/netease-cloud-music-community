package me.fanchaoo.dbo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * @author null
 * @date 2020-07-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 通知分类
     */
    private String category;

    /**
     * 通知类型
     */
    private String type;

    /**
     * 接收通知的用户id
     */
    private Long receiveUserId;

    /**
     * 发通知的用户id
     */
    private Long sendUserId;

    /**
     * 动态id
     */
    private Long momentId;

    /**
     * 父评论id
     */
    private Long parentCommentId;

    /**
     * 评论id
     */
    private Long commentId;

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