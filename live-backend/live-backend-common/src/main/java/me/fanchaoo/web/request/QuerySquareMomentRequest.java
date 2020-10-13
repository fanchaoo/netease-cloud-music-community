package me.fanchaoo.web.request;

import lombok.Data;
import me.fanchaoo.enums.EnumMomentType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QuerySquareMomentRequest {

    @NotNull
    @Min(1)
    private Integer pageNo;

    @NotNull
    @Min(1)
    @Max(20)
    private Integer pageSize;

    /**
     * 查询视频列表用到
     */
    private EnumMomentType momentType;

    /**
     * 查询视频列表用到
     */
    private Long repostMomentId;

    /**
     * 查询好友动态用到
     */
    private List<Long> userIdList;

    /**
     * 查询演出详情用到
     */
    private Long activityId;
}
