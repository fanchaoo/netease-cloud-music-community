package me.fanchaoo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ReplaceUtils {

    public static String translate(String remainStr, String templateStr, Pattern pattern, Map<String, Long> map, Set<String> matchedSet) {
        if (remainStr == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        boolean firstMatch = true;
        while (remainStr.length() > 0) {
            Matcher m = pattern.matcher(remainStr);
            if (!m.find()) {
                break;
            }

            if (firstMatch) {
                sb.append(remainStr.substring(0, m.start()));
                firstMatch = false;
            }

            String item = m.group(1);
            matchedSet.add(item);
            log.info("匹配到：" + item);
            sb.append(String.format(templateStr, item));
            remainStr = remainStr.substring(m.end());
        }
        sb.append(remainStr);
        System.out.println("剩余：" + remainStr);

        return sb.toString();
    }
}
