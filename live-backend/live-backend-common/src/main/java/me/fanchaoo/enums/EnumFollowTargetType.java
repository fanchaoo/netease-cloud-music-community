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
public enum EnumFollowTargetType {

    USER("USER", "用户"),
    TAG("TAG", "标签"),
    ;

    private String value;
    private String desc;

    public static final Map<String, String> value2desc = Arrays.stream(EnumFollowTargetType.values()).collect(Collectors.toMap(EnumFollowTargetType::getValue, EnumFollowTargetType::getDesc, (key1, key2) -> key2));

}
