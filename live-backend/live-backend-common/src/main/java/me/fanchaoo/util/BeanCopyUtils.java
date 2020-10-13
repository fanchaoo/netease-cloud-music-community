package me.fanchaoo.util;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class BeanCopyUtils {

    /**
     * 复制对象
     *
     * @param source
     * @param targetClazz
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T copy(S source, Class<T> targetClazz) {
        if (source == null) {
            return null;
        }

        T target = null;
        try {
            target = targetClazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 批量复制对象
     *
     * @param sources
     * @param targetClazz
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> copy(List<S> sources, Class<T> targetClazz) {
        List<T> targets = Lists.newArrayList();
        if (CollectionUtils.isEmpty(sources)) {
            return targets;
        }

        for (S source : sources) {
            try {
                targets.add(copy(source, targetClazz));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return targets;
    }


}
