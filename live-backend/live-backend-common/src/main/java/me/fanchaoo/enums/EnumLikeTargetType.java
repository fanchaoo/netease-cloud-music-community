package me.fanchaoo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum EnumLikeTargetType {

    MOMENT("MOMENT", "动态"),
    COMMENT("COMMENT", "评论"),
    ;

    private String value;
    private String desc;

    public static final Map<String, String> value2desc = Arrays.stream(EnumLikeTargetType.values()).collect(Collectors.toMap(EnumLikeTargetType::getValue, EnumLikeTargetType::getDesc, (key1, key2) -> key2));

}
