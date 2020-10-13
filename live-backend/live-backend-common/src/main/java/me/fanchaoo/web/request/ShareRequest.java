package me.fanchaoo.web.request;

import lombok.Data;
import me.fanchaoo.enums.EnumSharePageType;

import javax.validation.constraints.NotNull;

@Data
public class ShareRequest {

    @NotNull
    private EnumSharePageType pageType;

    private Long momentId;
}
