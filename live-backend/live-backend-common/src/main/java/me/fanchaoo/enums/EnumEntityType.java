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
public enum EnumEntityType {

    USER("USER", "用户"),
    ARTIST("ARTIST", "演出者"),
    ACTIVITY("ACTIVITY", "演出活动"),
    VENUE("VENUE", "场地"),
    MOMENT("MOMENT", "动态"),
    IMAGE("IMAGE", "图片"),
    VIDEO("VIDEO", "视频"),
    COMMENT("COMMENT", "评论"),
    TAG("TAG", "标签"),
    PRIVATE_LETTER("PRIVATE_LETTER", "私信"),
    ;

    private String value;
    private String desc;

    public static final Map<String, String> value2desc = Arrays.stream(EnumEntityType.values()).collect(Collectors.toMap(EnumEntityType::getValue, EnumEntityType::getDesc, (key1, key2) -> key2));

}
