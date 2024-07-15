package com.xlm.rpc.fault.tolerant;

/**
 * @author xlm
 * 2024/7/15 下午2:10
 */
public interface TolerantStrategyKeys {
    
    /**
     * 故障恢复
     */
    String FAIL_BACK = "failBack";

    /**
     * 快速失败
     */
    String FAIL_FAST = "failFast";

    /**
     * 故障转移
     */
    String FAIL_OVER = "failOver";

    /**
     * 静默处理
     */
    String FAIL_SAFE = "failSafe";
}
