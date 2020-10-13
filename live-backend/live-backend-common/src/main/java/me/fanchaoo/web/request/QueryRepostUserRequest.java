package me.fanchaoo.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class QueryRepostUserRequest {

    @NotNull
    private Long repostMomentId;

    @NotNull
    @Min(1)
    private Integer pageNo;

    @NotNull
    @Min(1)
    @Max(20)
    private Integer pageSize;

}
