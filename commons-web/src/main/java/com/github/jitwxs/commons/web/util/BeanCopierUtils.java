package com.github.jitwxs.commons.web.util;

import com.github.jitwxs.commons.core.constant.SymbolConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 浅拷贝工具类
 * @author jitwxs
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanCopierUtils {
    private static final Map<String, BeanCopier> CACHE = new ConcurrentHashMap<>();

    /**
     * 浅拷贝
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    private static BeanCopier getBeanCopier(Class<?> sourceClazz, Class<?> targetClazz) {
        String key = generatorKey(sourceClazz, targetClazz);
        BeanCopier copier;
        if(CACHE.containsKey(key)) {
            copier = CACHE.get(key);
        } else {
            copier = BeanCopier.create(sourceClazz, targetClazz, false);
            CACHE.put(key, copier);
        }
        return copier;
    }

    private static String generatorKey(Class<?> sourceClazz, Class<?> targetClazz) {
        return sourceClazz + SymbolConstant.UNDERLINE + targetClazz;
    }
}
