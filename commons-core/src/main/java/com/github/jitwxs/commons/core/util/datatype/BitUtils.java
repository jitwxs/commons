package com.github.jitwxs.commons.core.util.datatype;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 位操作工具类
 * @author jitwxs
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BitUtils {
    /**
     * 按位赋值，返回新值
     * @param value 原值
     * @param index 指定序号，从0开始
     * @param bit 指定位值，0 或 1
     * @return 新值
     */
    public static long setBitValue(long value, int index, int bit) {
        if(index < 0) {
            throw new IllegalArgumentException("index value must >= 0");
        }
        index += 1;

        if(getBitValue(value, index) == 1L) {
            if(bit == 0L) {
                value -= 1L << index - 1L;
            }
        } else if(bit == 1L) {
            value |= 1L << index - 1L;
        }
        return value;
    }

    /**
     * 获取指定位值
     * @param value 源数据
     * @param index 位数，从0开始
     * @return 位值
     */
    public static long getBitValue(long value, int index) {
        if(index < 0) {
            throw new IllegalArgumentException("index value must >= 0");
        }
        return value >> index & 1L;
    }
}
