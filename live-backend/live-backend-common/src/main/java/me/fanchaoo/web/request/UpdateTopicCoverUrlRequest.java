package me.fanchaoo.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateTopicCoverUrlRequest {

    @NotNull
    private Long topicId;

    @NotBlank
    private String coverUrl;
}
