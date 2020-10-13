package me.fanchaoo.web.request;

import lombok.Data;
import me.fanchaoo.enums.EnumMomentType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class QuerySquareImageRequest {

    @NotNull
    @Min(1)
    private Integer pageNo;

    @NotNull
    @Min(1)
    @Max(20)
    private Integer pageSize;
}
