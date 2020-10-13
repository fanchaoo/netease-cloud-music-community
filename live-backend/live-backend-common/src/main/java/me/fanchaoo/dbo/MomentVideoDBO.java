package me.fanchaoo.dbo;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * @author null
 * @date 2020-05-03
 */
@Data
public class MomentVideoDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 动态id
     */
    private Long momentId;

    /**
     * 视频标题
     */
    private String title;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 视频宽
     */
    private Integer width;

    /**
     * 视频高
     */
    private Integer height;

    /**
     * 视频大小
     */
    private Integer size;

    /**
     * 封面url
     */
    private String coverUrl;

    /**
     * 封面宽
     */
    private Integer coverWidth;

    /**
     * 封面高
     */
    private Integer coverHeight;

    /**
     * 收藏数量
     */
    private Integer collectCount;

    /**
     * 来源渠道
     */
    private String sourceChannel;

    /**
     * 来源url
     */
    private String sourceUrl;

    /**
     * 来源id
     */
    private String sourceId;

    /**
     * 排序字段
     */
    private Long sequence;

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