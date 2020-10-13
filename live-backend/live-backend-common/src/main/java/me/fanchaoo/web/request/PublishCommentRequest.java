package me.fanchaoo.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PublishCommentRequest {

    @NotNull
    private Long momentId;

    @NotBlank
    @Length(max = 200)
    private String content;

    @NotNull
    @Min(0)
    private Long parentId;

    @NotNull
    @Min(0)
    private Long replyToId;
}
