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
public enum EnumStatus {

    DISABLED(0, "已屏蔽"),
    NORMAL(1, "正常"),
    DELETED(2, "已删除"),
    ;

    private Integer value;
    private String desc;

    public static final Map<Integer, String> value2desc = Arrays.stream(EnumStatus.values()).collect(Collectors.toMap(EnumStatus::getValue, EnumStatus::getDesc, (key1, key2) -> key2));

}
