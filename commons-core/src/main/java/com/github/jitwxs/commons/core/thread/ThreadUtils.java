package com.github.jitwxs.commons.core.thread;

import com.github.jitwxs.commons.core.loop.Callback;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 线程工具类
 * @author jitwxs
 */
public class ThreadUtils {
    private static final Logger logger = Logger.getLogger(ThreadUtils.class.getName());

    public static void sleep(long timeout, TimeUnit unit, Callback callBack) {
        try {
            unit.sleep(timeout);
        } catch (InterruptedException e) {
            callBack.execute();
        }
    }

    public static void sleep(long timeout, TimeUnit unit) {
        try {
            unit.sleep(timeout);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "ThreadUtils#sleep error, class: " + Thread.currentThread().getName(), e);
        }
    }

    public static void sleep(long millis, Callback callBack) {
       sleep(millis, TimeUnit.MILLISECONDS, callBack);
    }

    public static void sleep(long millis) {
        sleep(millis, TimeUnit.MILLISECONDS);
    }

    /**
     * 异常堆栈
     */
    public static String getStackTrace() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if(ArrayUtils.isEmpty(elements)) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder(elements.length * 128);
        for(StackTraceElement element : elements) {
            sb.append(MessageFormat.format("{0}: {1}(): {2}", element.getClassName(), element.getMethodName(), element.getLineNumber()))
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }
}
