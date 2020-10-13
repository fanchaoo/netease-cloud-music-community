
package me.fanchaoo.web.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public <T> T getObject(String key, Class<T> clazz) {

        Object value = redisTemplate.boundValueOps(key).get();
        if (value == null) {
            return null;
        }

        if (value instanceof JSONObject) {
            return ((JSONObject) value).toJavaObject(clazz);
        }
        return (T) value;
    }

    public <T> List<T> getList(String key, Class<T> clazz) {

        Object value = redisTemplate.boundValueOps(key).get();
        if (value == null) {
            return null;
        }

        if (value instanceof JSONArray) {
            return ((JSONArray) value).toJavaList(clazz);
        }
        return (List<T>) value;
    }

    public <T> T getString(String key) {

        Object value = redisTemplate.boundValueOps(key).get();
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return (T) value;
        }
        return (T) value;
    }

    public void set(String key, Object value, long expireSecond) {
        redisTemplate.boundValueOps(key).set(value, expireSecond, TimeUnit.SECONDS);
    }


}
