package me.fanchaoo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum EnumProvider {

    weixin("weixin", "微信"),
    qq("qq", "QQ"),
    ;

    private String value;
    private String desc;

}
