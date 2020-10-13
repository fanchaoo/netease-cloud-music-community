package me.fanchaoo.web.request;

import lombok.Data;
import me.fanchaoo.enums.EnumFeedbackType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddFeedbackRequest {

    @NotNull
    private EnumFeedbackType type;

    @NotBlank
    @Length(max = 500)
    private String content;

    @Length(max = 50)
    private String contactInfo;
}
