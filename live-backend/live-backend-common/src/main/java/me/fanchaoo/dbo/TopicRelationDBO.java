package me.fanchaoo.dbo;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * 
 *
 * @author null
 * @date 2020-06-21
 */
@Data
public class TopicRelationDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 话题id
     */
    private Long topicId;

    /**
     * 动态id
     */
    private Long momentId;

    /**
     * 用户id
     */
    private Long userId;

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