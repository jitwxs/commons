package com.github.jitwxs.commons.core.thread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 默认线程异常处理器
 * @author jitwxs
 */
public enum  DefaultLogUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    INSTANCE;

    public DefaultLogUncaughtExceptionHandler getInstance(){
        return INSTANCE;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger.getLogger(DefaultLogUncaughtExceptionHandler.class.getName())
                .log(Level.SEVERE, "Uncaught Exception got, thread:" + t.getName(), e);
    }
}
