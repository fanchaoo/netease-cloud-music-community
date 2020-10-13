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
public enum EnumGender {

    WOMAN(0, "女"),
    MAN(1, "男"),
    ;

    private Integer value;
    private String desc;

    public static final Map<Integer, String> value2desc = Arrays.stream(EnumGender.values()).collect(Collectors.toMap(EnumGender::getValue, EnumGender::getDesc, (key1, key2) -> key2));

}
