package com.github.jitwxs.commons.core.date;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Objects;

/**
 * @author jitwxs
 * @date 2020年05月16日 22:42
 */
public class DateFormatUtils {

    /**
     * 日期字符串 -> LocalDateTime
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
     * LocalDateTime -> 日期字符串
     */
    public static String formatDate(LocalDateTime localDateTime, DateTimeFormatter formatter) {
        Objects.requireNonNull(localDateTime, "localDateTime params not allow null");
        Objects.requireNonNull(formatter, "formatter params not allow null");

        return formatter.format(localDateTime);
    }

    /**
     * Timestamp -> 日期字符串
     */
    public static String formatTimestamp(long timestamp, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        return formatter.format(localDateTime);
    }

    /**
     * 日期字符串 -> Date
     */
    public static Date parserDate(String dateStr, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = parserDateTime(dateStr, formatter);

        return DateUtils.parserDate(localDateTime);
    }

    /**
     * Date -> 日期字符串
     */
    public static String formatDate(Date date, DateTimeFormatter formatter) {
        Objects.requireNonNull(date, "date params not allow null");

        return formatDate(DateUtils.parserDateTime(date), formatter);
    }
}
