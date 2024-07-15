package com.xlm.rpc.retry;

import com.xlm.rpc.fault.retry.FixedIntervalRetryStrategy;
import com.xlm.rpc.fault.retry.RetryStrategy;
import com.xlm.rpc.model.RpcResponse;
import org.junit.Test;

/**
 * @author xlm
 * 2024/7/15 下午1:14
 */
public class RetryStrategyTest {

    private RetryStrategy retryStrategy = new FixedIntervalRetryStrategy();
    @Test
    public void test() {
        RpcResponse rpcResponse = new RpcResponse();
        try {
            rpcResponse = retryStrategy.doRetry(() -> {
                System.out.println("test retry strategy");
                throw new RuntimeException("test retry strategy");
            });
            System.out.println("response is " + rpcResponse);
        } catch (Exception e) {
            System.out.println("fail retry strategy");
            throw new RuntimeException(e);
        }
    }
}
