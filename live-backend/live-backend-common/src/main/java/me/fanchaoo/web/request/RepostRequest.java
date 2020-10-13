package me.fanchaoo.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RepostRequest {

    @NotNull
    private Long repostMomentId;

    @NotBlank
    @Length(max = 250)
    private String textContent;
}
