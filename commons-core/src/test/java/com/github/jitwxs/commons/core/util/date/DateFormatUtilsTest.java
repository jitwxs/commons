package com.github.jitwxs.commons.core.util.date;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class DateFormatUtilsTest {

    @Test
    public void parserDateTime() {
        LocalDateTime dateTime = DateFormatUtils.parserDateTime(null, null);
    }
}