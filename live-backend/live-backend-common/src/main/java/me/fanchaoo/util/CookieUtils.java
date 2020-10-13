package me.fanchaoo.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author fanchao
 * @date 2019/8/6 6:44 PM
 */
public class CookieUtils {

    public static String getCookieToken(String tokenKey, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (org.apache.commons.lang3.StringUtils.equals(tokenKey, cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
