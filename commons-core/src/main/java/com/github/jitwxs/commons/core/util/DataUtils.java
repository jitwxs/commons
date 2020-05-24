package com.github.jitwxs.commons.core.util;

import com.google.common.base.Joiner;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 集合处理工具类
 * @author jitwxs
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataUtils {
    /**
     * 将集合转为分隔符分割的字符串
     *
     * @param data 原数据
     * @param separator 分隔符
     * @return 集合字符串
     */
    public static String collectionToStr(Iterable<?> data, String separator) {
        if(data == null) {
            return StringUtils.EMPTY;
        }
        return Joiner.on(separator).join(data);
    }

    /**
     * 获取 list 中第一个元素
     * @param list 集合
     * @return 第一个元素，不存在返回 null
     */
    public static <T> T getListFirst(List<T> list) {
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }
}
