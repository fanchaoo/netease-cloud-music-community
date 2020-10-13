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
public enum EnumBoolean {

    NO(0, "否"),
    YES(1, "是"),
    ;

    private Integer value;
    private String desc;

    public static final Map<Integer, String> value2desc = Arrays.stream(EnumBoolean.values()).collect(Collectors.toMap(EnumBoolean::getValue, EnumBoolean::getDesc, (key1, key2) -> key2));

}
