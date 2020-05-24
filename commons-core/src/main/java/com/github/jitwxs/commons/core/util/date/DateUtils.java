package com.github.jitwxs.commons.core.util.date;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * 日期工具类
 * @author jitwxs
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
    /**
     * LocalDateTime 转 Date
     *
     * @param localDateTime 源数据
     * @return Date 类型数据
     */
    public static Date parserDate(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime, "localDateTime params not allow null");

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date 源数据
     * @return LocalDateTime 类型数据
     */
    public static LocalDateTime parserDateTime(Date date) {
        Objects.requireNonNull(date, "date params not allow null");

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime 转 Timestamp
     *
     * @param localDateTime 源数据
     * @return 时间戳
     */
    public static Long toEpochMilli(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime, "localDateTime params not allow null");

        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
