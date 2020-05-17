package com.github.jitwxs.commons.core.date;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Objects;

/**
 * 日期/字符串转换工具类
 * @author jitwxs
 */
public class DateFormatUtils {

    /**
     * 日期字符串 转 LocalDateTime
     *
     * @param dateStr 日期字符串
     * @param formatter 格式化规则
     * @return LocalDateTime
     */
    public static LocalDateTime parserDateTime(String dateStr, DateTimeFormatter formatter) {
        Objects.requireNonNull(dateStr, "dateStr params not allow null");
        if(StringUtils.isBlank(dateStr)) {
            return null;
        }

        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            localDateTime = LocalDateTime.of(LocalDate.parse(dateStr, formatter), LocalTime.MIDNIGHT);
        }
        return localDateTime;
    }

    /**
     * LocalDateTime 转 日期字符串
     *
     * @param localDateTime 日期
     * @param formatter 格式化规则
     * @return 日期字符串
     */
    public static String formatDate(LocalDateTime localDateTime, DateTimeFormatter formatter) {
        Objects.requireNonNull(localDateTime, "localDateTime params not allow null");
        Objects.requireNonNull(formatter, "formatter params not allow null");

        return formatter.format(localDateTime);
    }

    /**
     * Timestamp 转 日期字符串
     *
     * @param timestamp 时间戳
     * @param formatter 格式化规则
     * @return 日期字符串
     */
    public static String formatTimestamp(long timestamp, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        return formatter.format(localDateTime);
    }

    /**
     * 日期字符串 转 Date
     *
     * @param dateStr 日期字符串
     * @param formatter 格式化规则
     * @return Date
     */
    public static Date parserDate(String dateStr, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = parserDateTime(dateStr, formatter);

        return DateUtils.parserDate(localDateTime);
    }

    /**
     * Date 转 日期字符串
     *
     * @param date Date
     * @param formatter 格式化规则
     * @return 日期字符串
     */
    public static String formatDate(Date date, DateTimeFormatter formatter) {
        Objects.requireNonNull(date, "date params not allow null");

        return formatDate(DateUtils.parserDateTime(date), formatter);
    }
}
