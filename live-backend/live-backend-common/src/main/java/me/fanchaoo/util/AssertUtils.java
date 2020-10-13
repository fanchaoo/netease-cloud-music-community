package me.fanchaoo.util;


import me.fanchaoo.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

public class AssertUtils {

    public static void notNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new BusinessException(-1, message);
        }
    }

    public static void notBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BusinessException(-1, message);
        }
    }

    public static void isTrue(Boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(-1, message);
        }
    }

    public static void isFalse(Boolean expression, String message) {
        if (expression) {
            throw new BusinessException(-1, message);
        }
    }

    public static void equals(Object a, Object b, String message) {
        if (!Objects.equals(a, b)) {
            throw new BusinessException(-1, message);
        }
    }

    public static void notEmpty(Collection collection, String message) {
        if (collection == null || collection.size() == 0) {
            throw new BusinessException(-1, message);
        }
    }
}
