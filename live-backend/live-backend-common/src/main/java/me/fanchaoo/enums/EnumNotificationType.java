package me.fanchaoo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum EnumNotificationType {

    /**
     * 评论
     */

    // receiveUserId（通过moment查找），momentId，commentId
    COMMENT_TO_MOMENT("COMMENT_TO_MOMENT", "COMMENT", "评论动态"),
    // receiveUserId（通过parentComment查找），momentId，parentCommentId，commentId
    REPLY_TO_MOMENT_COMMENT("REPLY_TO_MOMENT_COMMENT", "COMMENT", "回复动态评论"),

    /**
     * @
     */

    // receiveUserId（通过原moment查找），momentId（转发后的新momentId）
    REPOST("REPOST", "AT", "转发"),
    // receiveUserId，momentId
    AT_ON_MOMENT("AT_ON_MOMENT", "AT", "动态中@"),
    // receiveUserId，momentId，commentId
    AT_ON_COMMENT("AT_ON_COMMENT", "AT", "评论中@"),
    // receiveUserId，momentId，parentCommentId，commentId
    AT_ON_CHILDREN_COMMENT("AT_ON_CHILDREN_COMMENT", "AT", "子评论中@"),

    /**
     * 通知
     */

    // receiveUserId，momentId
    COLLECT_VIDEO("COLLECT_VIDEO", "NOTIFICATION", "收藏视频"),
    // receiveUserId，momentId
    LIKE_MOMENT("LIKE_MOMENT", "NOTIFICATION", "点赞动态"),
    // receiveUserId，momentId
    LIKE_VIDEO("LIKE_VIDEO", "NOTIFICATION", "点赞视频"),
    // receiveUserId，momentId，commentId
    LIKE_COMMENT("LIKE_COMMENT", "NOTIFICATION", "点赞评论"),
    // receiveUserId，momentId，parentCommentId，commentId
    LIKE_CHILDREN_COMMENT("LIKE_CHILDREN_COMMENT", "NOTIFICATION", "点赞子评论"),
    // receiveUserId，momentId
    SHARE_MOMENT("SHARE_MOMENT", "NOTIFICATION", "分享动态"),
    // receiveUserId，momentId
    SHARE_VIDEO("SHARE_VIDEO", "NOTIFICATION", "分享视频"),
    // receiveUserId
    FOLLOW_USER("FOLLOW_USER", "NOTIFICATION", "关注用户"),
    ;

    private String value;
    private String category;
    private String desc;

    public static final Map<String, EnumNotificationType> value2enum = Arrays.stream(EnumNotificationType.values()).collect(Collectors.toMap(EnumNotificationType::getValue, Function.identity(), (key1, key2) -> key2));

}
