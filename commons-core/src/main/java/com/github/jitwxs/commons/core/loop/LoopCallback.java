package com.github.jitwxs.commons.core.loop;

/**
 * @author jitwxs
 * @date 2020年05月16日 17:17
 */
public interface LoopCallback {
    /**
     * 回调接口
     * @param times 执行次数
     * @return 是否继续执行；true：继续；false：退出
     */
    boolean execute(int times);
}
