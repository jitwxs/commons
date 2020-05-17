package com.github.jitwxs.commons.core.loop;

/**
 * @author jitwxs
 */
public interface LoopCallback {
    /**
     * 回调接口
     * @param times 执行次数
     * @return 是否继续执行；true：继续；false：退出
     */
    boolean execute(int times);
}
