package me.fanchaoo.util;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.web.dto.LoginUserDTO;

@Slf4j
public class UserContext {

    private static final ThreadLocal<LoginUserDTO> THREAD_LOCAL = new ThreadLocal<>();

    public static void setLoginUser(LoginUserDTO loginUser) {
        AssertUtils.notNull(loginUser, "loginUser can not be null");
        THREAD_LOCAL.set(loginUser);
    }

    public static LoginUserDTO getLoginUser() {
        LoginUserDTO loginUser = THREAD_LOCAL.get();
        AssertUtils.notNull(loginUser, "无法获取当前登录用户信息");
        return loginUser;
    }

    public static Long getArtistId() {
        return getLoginUser().getArtistId();
    }

    public static String getRedisKeyPrefix() {
        return getLoginUser().getArtistName();
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
