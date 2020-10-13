package me.fanchaoo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum EnumNotificationCategory {

    COMMENT("COMMENT", "评论"),
    AT("AT", "@"),
    NOTIFICATION("NOTIFICATION", "通知"),
    ;

    private String value;
    private String desc;
}
