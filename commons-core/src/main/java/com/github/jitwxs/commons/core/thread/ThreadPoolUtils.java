package com.github.jitwxs.commons.core.thread;

import com.github.jitwxs.commons.core.loop.Callback;
import com.github.jitwxs.commons.core.constant.Symbol;
import com.google.common.base.Joiner;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * 线程池工具类
 * @author jitwxs
 */
public class ThreadPoolUtils {
    private static final Logger logger = Logger.getLogger(ThreadUtils.class.getName());

    /**
     * 无界队列
     *
     * @param size 线程数
     * @param name 线程池名
     * @return 线程池
     */
    public static ThreadPoolExecutor unBondQueueExecutor(int size, Object... name) {
        return unBondQueueExecutor(size, DefaultLogUncaughtExceptionHandler.INSTANCE, name);
    }

    /**
     * 无界队列
     *
     * @param size 线程数
     * @param handler 异常处理器
     * @param name 线程池名
     * @return 线程池
     */
    public static ThreadPoolExecutor unBondQueueExecutor(int size, Thread.UncaughtExceptionHandler handler, Object... name) {
        ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
        builder.setUncaughtExceptionHandler(handler);
        if(Objects.nonNull(name)) {
            builder.setNameFormat(Joiner.on(Symbol.LINE).join(name));
        }

        return new ThreadPoolExecutor(size, size, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                builder.build(), new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 有界队列
     *
     * @param core 核心线程数
     * @param max 最大线程数
     * @param maxQueueSize 队列上限
     * @param keepAlive 最大线程数存活时间
     * @param name 线程池名
     * @return 线程池
     */
    public static ThreadPoolExecutor bondQueueExecutor(int core, int max, int keepAlive, int maxQueueSize, Object... name) {
        return bondQueueExecutor(core, max, keepAlive, maxQueueSize, DefaultLogUncaughtExceptionHandler.INSTANCE, name);
    }

    /**
     * 有界队列
     *
     * @param core 核心线程数
     * @param max 最大线程数
     * @param maxQueueSize 队列上限
     * @param name 线程池名
     * @return 线程池
     */
    public static ThreadPoolExecutor bondQueueExecutor(int core, int max, int keepAlive, int maxQueueSize,
                                                       Thread.UncaughtExceptionHandler handler, Object... name) {
        ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
        builder.setUncaughtExceptionHandler(handler);
        if(Objects.nonNull(name)) {
            builder.setNameFormat(Joiner.on(Symbol.LINE).join(name));
        }

        return new ThreadPoolExecutor(core, max, keepAlive, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(maxQueueSize),
                builder.build(), new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 定时执行，线程池
     *
     * @param core 核心线程数
     * @param name 线程池名
     * @return 定时执行线程池
     */
    public static ScheduledThreadPoolExecutor scheduledExecutor(int core, Object... name) {
        return scheduledExecutor(core, DefaultLogUncaughtExceptionHandler.INSTANCE, name);
    }

    /**
     * 定时执行，线程池
     *
     * @param core 核心线程数
     * @param handler 异常处理器
     * @param name 线程池名
     * @return 定时执行线程池
     */
    public static ScheduledThreadPoolExecutor scheduledExecutor(int core, Thread.UncaughtExceptionHandler handler, Object... name) {
        ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
        builder.setUncaughtExceptionHandler(handler);
        builder.setDaemon(true);
        if(Objects.nonNull(name)) {
            builder.setNameFormat(Joiner.on(Symbol.LINE).join(name));
        }

        return new ScheduledThreadPoolExecutor(core, builder.build());
    }

    /**
     * 停止线程池
     *
     * @param executor 线程池
     * @param waitTimeout 调用停止，等待时间
     * @param callback 停止异常时，处理方法
     */
    public static void shutdown(ExecutorService executor, long waitTimeout, Callback callback) {
        if(executor != null) {
            executor.shutdown();
            try {
                while (!executor.awaitTermination(waitTimeout, TimeUnit.MILLISECONDS)) {
                    logger.info("ThreadPoolUtils#shutdown is not shutdown yet");
                    TimeUnit.MILLISECONDS.sleep(waitTimeout);
                }
            } catch (InterruptedException e) {
                callback.execute();
            }
        }
    }
}

