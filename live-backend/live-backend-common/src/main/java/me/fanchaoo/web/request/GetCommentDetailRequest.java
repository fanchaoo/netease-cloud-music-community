package me.fanchaoo.web.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class GetCommentDetailRequest {

    @NotNull
    private Long commentId;

}
