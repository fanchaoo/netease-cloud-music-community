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
public enum EnumResponseCode {

    TOKEN_INVALID(100001, "登录token非法"),
    TOKEN_EXPIRED(100002, "登录token已过期"),
    VIDEO_PARAM_REQUIRES(100003, "video不能为空"),
    COMMENT_NOT_EXISTS(100004, "comment不存在"),
    ;

    private Integer value;
    private String desc;

    public static final Map<Integer, String> value2desc = Arrays.stream(EnumResponseCode.values()).collect(Collectors.toMap(EnumResponseCode::getValue, EnumResponseCode::getDesc, (key1, key2) -> key2));

}
