package com.github.jitwxs.commons.core.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * @author jitwxs
 * @date 2020年05月16日 16:59
 */
public class DateUtils {
    /**
     * LocalDateTime -> Date
     */
    public static Date parserDate(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime, "localDateTime params not allow null");

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date -> LocalDateTime
     */
    public static LocalDateTime parserDateTime(Date date) {
        Objects.requireNonNull(date, "date params not allow null");

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime -> Timestamp
     */
    public static Long toEpochMilli(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime, "localDateTime params not allow null");

        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


}
