package me.fanchaoo.dbo;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * @author null
 * @date 2019-12-27
 */
@Data
public class TagDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 创建人id
     */
    private Long createUserId;

    private Integer fansCount;

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