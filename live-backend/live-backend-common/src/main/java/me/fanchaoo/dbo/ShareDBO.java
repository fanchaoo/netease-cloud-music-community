package me.fanchaoo.dbo;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * @author null
 * @date 2019-12-27
 */
@Data
public class ShareDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 页面类型
     */
    private String pageType;

    /**
     * 动态id
     */
    private Long momentId;

    /**
     * 用户id
     */
    private Long profileUserId;

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