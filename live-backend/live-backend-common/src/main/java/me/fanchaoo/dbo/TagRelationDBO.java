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
public class TagRelationDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 目标类型（MOMENT：动态）
     */
    private String entityType;

    /**
     * 目标id
     */
    private Long entityId;

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

    public TagRelationDBO() {
    }

    public TagRelationDBO(Long tagId, String entityType, Long entityId, Long artistId) {
        this.tagId = tagId;
        this.entityType = entityType;
        this.entityId = entityId;
        this.artistId = artistId;
    }
}