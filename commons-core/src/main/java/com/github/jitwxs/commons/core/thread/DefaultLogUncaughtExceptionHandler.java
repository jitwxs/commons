package com.github.jitwxs.commons.core.thread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jitwxs
 * @date 2020年05月16日 17:05
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
