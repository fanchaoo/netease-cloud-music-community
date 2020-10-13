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
public enum EnumFriendType {

    FOLLOW("FOLLOW", "关注"),
    FANS("FANS", "粉丝"),
    ;

    private String value;
    private String desc;

    public static final Map<String, String> value2desc = Arrays.stream(EnumFriendType.values()).collect(Collectors.toMap(EnumFriendType::getValue, EnumFriendType::getDesc, (key1, key2) -> key2));

}
