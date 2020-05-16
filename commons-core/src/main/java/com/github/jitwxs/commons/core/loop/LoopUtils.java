package com.github.jitwxs.commons.core.loop;

import java.util.concurrent.TimeUnit;

/**
 * @author jitwxs
 * @date 2020年05月16日 20:41
 */
public class LoopUtils {
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
                TimeUnit.MILLISECONDS.sleep(millis == 0L ? 500 : millis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            times++;
        }
    }
}
