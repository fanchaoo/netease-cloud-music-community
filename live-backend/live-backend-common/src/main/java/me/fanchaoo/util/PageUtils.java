package me.fanchaoo.util;


import me.fanchaoo.exception.BusinessException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanchao
 * @date 2019/8/3 8:28 PM
 */
public class PageUtils {

    private static void checkPageNoAndPageSize(Integer pageNo, Integer pageSize) {

        AssertUtils.notNull(pageNo, "pageNo");
        AssertUtils.notNull(pageSize, "pageSize不能为空");

        if (pageNo <= 0) {
            throw new BusinessException("pageNo须为正数");
        }

        if (pageSize <= 0) {
            throw new BusinessException("pageSize须为正数");
        }
    }

    public static int getStart(Integer pageNo, Integer pageSize) {

        checkPageNoAndPageSize(pageNo, pageSize);
        return (pageNo - 1) * pageSize;
    }

    /**
     * 假分页
     *
     * @param data
     * @param pageNo
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> List<T> getPageData(List<T> data, Integer pageNo, Integer pageSize) {

        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>();
        }

        int start = getStart(pageNo, pageSize);

        if (start > data.size() - 1) {
            return new ArrayList<>();
        }

        int end = Math.min((start + pageSize), data.size());
        return data.subList(start, end);
    }
}
