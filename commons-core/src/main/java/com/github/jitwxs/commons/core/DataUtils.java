package com.github.jitwxs.commons.core;

import com.google.common.base.Joiner;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author jitwxs
 * @date 2020年05月16日 21:40
 */
public class DataUtils {
    /**
     * 将集合转为分隔符分割的字符串
     * @param data 原数据
     * @param separator 分隔符
     */
    public static String collectionToStr(Iterable<?> data, String separator) {
        if(data == null) {
            return StringUtils.EMPTY;
        }
        return Joiner.on(separator).join(data);
    }

    public static <T> T getListFirst(List<T> list) {
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

}
