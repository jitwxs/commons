package com.github.jitwxs.commons.core.util.thread;

import org.junit.Test;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ThreadPoolUtilsTest {
    private static final Logger logger = Logger.getLogger(ThreadPoolUtilsTest.class.getName());

    @Test
    public void shutdown() {
        ThreadPoolExecutor executor = ThreadPoolUtils.unBondQueueExecutor(1, "test-pool-utils-test");
        executor.execute(() -> {
            ThreadUtils.sleep(5, TimeUnit.SECONDS);
        });

        ThreadPoolUtils.shutdown(executor, 1000, () -> {
            logger.info("ThreadPoolUtilsTest shutdown test error..");
        });
    }
}