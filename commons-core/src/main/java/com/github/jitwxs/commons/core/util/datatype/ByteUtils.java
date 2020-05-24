package com.github.jitwxs.commons.core.util.datatype;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

/**
 * 字节工具类
 * @author jitwxs
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ByteUtils {
    /**
     * long 转 byte数组
     *
     * @param value 源数据
     * @return byte数组
     */
    public static byte[] longToBytes(long value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        return byteBuffer.putLong(value).array();
    }

    /**
     * byte数组 转 long
     *
     * @param input 源数据
     * @param offset 偏移下标，正常情况为 0
     * @param isLittleEndian 是否是小端字节序
     * @return long结果
     */
    public static long byteToLong(byte[] input, int offset, boolean isLittleEndian) {
        Objects.requireNonNull(input, "byte[] array not allow null");

        ByteBuffer byteBuffer = ByteBuffer.wrap(input, offset, 8);
        if(isLittleEndian) {
            // 默认为大端字节序
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        }
        return byteBuffer.getLong();
    }

    /**
     * int 转 byte数组
     *
     * @param value 源数据
     * @return byte数组
     */
    public static byte[] intToBytes(int value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        return byteBuffer.putInt(value).array();
    }

    /**
     * 合并两个byte数组
     *
     * @param bytes1 字节数组1
     * @param bytes2 字节数组2
     * @return 合并后的数组
     */
    public static byte[] merge(byte[] bytes1, byte[] bytes2) {
        Objects.requireNonNull(bytes1, "byte[] array not allow null");
        Objects.requireNonNull(bytes2, "byte[] array not allow null");

        byte[] result = new byte[bytes1.length + bytes2.length];
        System.arraycopy(bytes1, 0, result, 0, bytes1.length);
        System.arraycopy(bytes2, 0, result, bytes1.length, bytes2.length);
        return result;
    }
}
