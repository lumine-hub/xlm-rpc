package com.xlm.rpc.fault.retry;

import com.xlm.rpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @author xlm
 * 2024/7/15 下午12:59
 */
public interface RetryStrategy {
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
