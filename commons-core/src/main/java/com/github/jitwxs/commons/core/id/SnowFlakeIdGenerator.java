package com.github.jitwxs.commons.core.id;

import com.github.jitwxs.commons.core.util.TimeUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 雪花算法ID生成器
 *
 * 64bit = 符号位(1bit) + 时间戳(41bit) + 业务线id(3bit) + 机器id(7bit) + 毫秒内序列(12bit)
 *
 * @author jitwxs
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SnowFlakeIdGenerator {
    /**
     * 初始化id时间(标记时间)：2020-01-01 00:00:00
     */
    private static final long INITIAL_TIME_STAMP = 1577808000000L;
    /**
     * 业务线id所占的位数
     */
    private static final long BUSINESS_ID_BITS = 3L;
    /**
     * 业务线机器id所占的位数
     */
    private static final long SERVER_ID_BITS = 7L;
    /**
     * 支持的最大业务线id 7 (节点数0~7，共8个)
     */
    private static final long MAX_BUSINESS_ID = ~(-1L << BUSINESS_ID_BITS);
    /**
     * 支持的业务线最大机器id 127 (节点数0~127，共128个)
     */
    private static final long MAX_SERVER_ID = ~(-1L << SERVER_ID_BITS);
    /**
     * 序列在id中占的位数12位
     */
    private static final long SEQUENCE_BITS = 12L;
    /**
     * 机器id的偏移量(左移12位)
     */
    private static final long SERVER_ID_LEFT_SHIFT = SEQUENCE_BITS;
    /**
     * 业务线id的偏移量(左移12+7=19位)
     */
    private static final long BUSINESS_ID_LEFT_SHIFT = SEQUENCE_BITS + SERVER_ID_BITS;
    /**
     * 时间毫秒数的偏移量(左移12+7+3=22位)
     */
    private static final long TIME_STAMP_LEFT_SHIFT = SEQUENCE_BITS + SERVER_ID_BITS + BUSINESS_ID_BITS;
    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    /**
     * 业务线id (0~7)
     */
    private long businessId;
    /**
     * 机器节点id (0~127)
     */
    private long serverId;
    /**
     * 毫秒内序列 (0~4095)
     */
    private long sequence = 0L;
    /**
     * 上次生成id的时间截
     */
    private long lastTimestamp = -1L;

    private static final String MSG_1 = "Server Id can't be greater than %d or less than 0";
    private static final String MSG_2 = "Business Id can't be greater than %d or less than 0";
    private static final String MSG_3 = "Clock moved backwards. Refusing to generate id for %d milliseconds";

    /**
     * 初始化生成器
     *
     * @param businessId 业务线id
     * @param serverId   业务线下机器id
     */
    public SnowFlakeIdGenerator(final long businessId, final long serverId) {
        if (businessId > MAX_BUSINESS_ID || businessId < 0) {
            throw new IllegalArgumentException(String.format(MSG_2, MAX_BUSINESS_ID));
        }
        if (serverId > MAX_SERVER_ID || serverId < 0) {
            throw new IllegalArgumentException(String.format(MSG_1, MAX_SERVER_ID));
        }
        this.businessId = businessId;
        this.serverId = serverId;
    }

    /**
     * 获得下一个id
     * <p>
     * 0     000000000000000000000000000000000000000000       000              0000000     000000000000
     * sign  timestamp                                        businessId       serverId    sequence
     */
    public synchronized long nextId() {
        // 获取当前毫秒数
        long timestamp = TimeUtils.nowMs();
        // 如果服务器时间有问题(时钟后退)报错
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format(MSG_3, this.lastTimestamp - timestamp));
        }
        // 如果上次生成时间和当前时间相同,在同一毫秒内，则进行毫秒内序列生成
        if (this.lastTimestamp == timestamp) {
            // sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
            this.sequence = (this.sequence + 1) & SEQUENCE_MASK;
            // 判断是否溢出,也就是每毫秒内超过4095，当为4096时，与sequenceMask相与，sequence就等于0
            if (this.sequence == 0) {
                // sequence等于0说明毫秒内序列已经增长到最大值，自旋等待到下一毫秒
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            // 如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加
            this.sequence = 0L;
        }
        this.lastTimestamp = timestamp;
        // 最后，按照规则，移位并通过或运算拼到一起组成64位的id
        // 0     000000000000000000000000000000000000000000       000              0000000     000000000000
        // sign  timestamp                                        businessId       serverId    sequence
        return ((timestamp - INITIAL_TIME_STAMP) << TIME_STAMP_LEFT_SHIFT)
                | (this.businessId << BUSINESS_ID_LEFT_SHIFT)
                | (this.serverId << SERVER_ID_LEFT_SHIFT) | this.sequence;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = TimeUtils.nowMs();
        while (timestamp <= lastTimestamp) {
            timestamp = TimeUtils.nowMs();
        }
        return timestamp;
    }
}
