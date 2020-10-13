package me.fanchaoo.web.request;

import lombok.Data;
import me.fanchaoo.enums.EnumNotificationCategory;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ClearNotificationCountRequest {

    @NotNull
    private EnumNotificationCategory category;

}
