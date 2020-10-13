package me.fanchaoo.web.request;

import lombok.Data;
import me.fanchaoo.enums.EnumLikeTargetType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class QueryLikeUserRequest {

    @NotNull
    private EnumLikeTargetType targetType;

    @NotNull
    private Long targetId;

    @NotNull
    @Min(1)
    private Integer pageNo;

    @NotNull
    @Min(1)
    @Max(20)
    private Integer pageSize;

}
