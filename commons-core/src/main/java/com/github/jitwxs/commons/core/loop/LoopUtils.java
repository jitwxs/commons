package com.github.jitwxs.commons.core.loop;

import java.util.concurrent.TimeUnit;

/**
 * 通用循环工具类
 * @author jitwxs
 */
public class LoopUtils {
    private static final int DEFAULT_SLEEP = 500;

    /**
     * 循环方法
     * @param millis 单次执行间隔
     * @param exceptionDesc 异常描述
     * @param callback 回调方法
     */
    public static void loop(long millis, String exceptionDesc, LoopCallback callback) {
        int times = 0;
        while (callback.execute(times)) {
            try {
                TimeUnit.MILLISECONDS.sleep(millis == 0L ? DEFAULT_SLEEP : millis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            times++;
        }
    }
}
