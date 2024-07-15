package com.xlm.rpc.fault.retry;

/**
 * @author xlm
 * 2024/7/15 下午1:21
 */
public interface RetryStrategyKeys {
    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";

}
