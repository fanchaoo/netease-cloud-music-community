package me.fanchaoo.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteCommentRequest {

    @NotNull
    private Long commentId;
}
