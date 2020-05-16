package com.github.jitwxs.commons.core.date;

/**
 * @author jitwxs
 * @date 2020年05月16日 16:59
 */
public class TimeUtils {
    public static long nowMs() {
        return System.currentTimeMillis();
    }

    public static long diffMs(long startMillis) {
       return diffMs(startMillis, nowMs());
    }

    public static long diffMs(long startMillis, long endMillis) {
        return endMillis - startMillis;
    }

    public static long nowNano() {
        return System.nanoTime();
    }

    public static long diffNano(long startNano) {
        return diffNano(startNano, nowNano());
    }

    public static long diffNano(long startNano, long endNano) {
        return endNano - startNano;
    }
}
