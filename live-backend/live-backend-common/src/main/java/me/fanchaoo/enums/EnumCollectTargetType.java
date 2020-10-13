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
public enum EnumCollectTargetType {

    VIDEO("VIDEO", "视频"),
    ;

    private String value;
    private String desc;

    public static final Map<String, String> value2desc = Arrays.stream(EnumCollectTargetType.values()).collect(Collectors.toMap(EnumCollectTargetType::getValue, EnumCollectTargetType::getDesc, (key1, key2) -> key2));

}
