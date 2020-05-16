package com.github.jitwxs.commons.core.thread;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ThreadUtilsTest {
    private static final Logger logger = Logger.getLogger(ThreadUtilsTest.class.getName());

    @Test
    public void sleep() {
        Thread t1 = new Thread(() -> ThreadUtils.sleep(10, TimeUnit.SECONDS, () ->logger.info("running error..")));

        t1.start();

        new Thread(t1::interrupt).start();
    }
}