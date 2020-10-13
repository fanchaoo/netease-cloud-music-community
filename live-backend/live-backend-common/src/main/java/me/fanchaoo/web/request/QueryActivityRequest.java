package me.fanchaoo.web.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class QueryActivityRequest {

    @NotNull
    @Min(1)
    private Integer pageNo;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer pageSize;
}
