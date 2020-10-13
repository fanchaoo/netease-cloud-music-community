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
public enum EnumTagRelationEntityType {

    MOMENT("MOMENT", "动态"),
    ;

    private String value;
    private String desc;

    public static final Map<String, String> value2desc = Arrays.stream(EnumTagRelationEntityType.values()).collect(Collectors.toMap(EnumTagRelationEntityType::getValue, EnumTagRelationEntityType::getDesc, (key1, key2) -> key2));

}
