package me.fanchaoo.util;

import java.util.UUID;


public class UUIDUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
