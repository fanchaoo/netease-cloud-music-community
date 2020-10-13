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
public enum EnumFeedbackType {

    QUESTION("QUESTION", "问题"),
    SUGGESTION("SUGGESTION", "建议"),
    OTHER("OTHER", "其它"),
    ;

    private String value;
    private String desc;


}
