package me.fanchaoo.web.request;

import lombok.Data;
import me.fanchaoo.enums.EnumCollectTargetType;

import javax.validation.constraints.NotNull;

@Data
public class CancelCollectRequest {

    @NotNull
    private EnumCollectTargetType targetType;

    @NotNull
    private Long targetId;
}
