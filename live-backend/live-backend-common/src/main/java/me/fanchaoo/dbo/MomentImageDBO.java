package me.fanchaoo.dbo;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * @author null
 * @date 2019-12-27
 */
@Data
public class MomentImageDBO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 动态id
     */
    private Long momentId;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 宽
     */
    private Integer width;

    /**
     * 高
     */
    private Integer height;

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

    public MomentImageDBO() {
    }

    public MomentImageDBO(Long momentId, String url, Integer width, Integer height, Long artistId) {
        this.momentId = momentId;
        this.url = url;
        this.width = width;
        this.height = height;
        this.artistId = artistId;
    }
}