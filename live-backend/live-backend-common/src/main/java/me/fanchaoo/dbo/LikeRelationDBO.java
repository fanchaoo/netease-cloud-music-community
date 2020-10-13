package me.fanchaoo.dbo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * @author null
 * @date 2019-12-27
 */
@Data
public class LikeRelationDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 目标类型（MOMENT：动态，COMMENT：评论）
     */
    private String targetType;

    /**
     * 目标id
     */
    private Long targetId;

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

    public LikeRelationDBO() {
    }

    public LikeRelationDBO(Long userId, String targetType, Long targetId, Long artistId) {
        this.userId = userId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.artistId = artistId;
    }
}