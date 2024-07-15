package com.xlm.rpc.fault.retry;

import com.xlm.rpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @author xlm
 * 2024/7/15 下午1:06
 */
public class NoRetryStrategy implements RetryStrategy {
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
