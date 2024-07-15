package com.xlm.rpc.fault.tolerant;

import com.xlm.rpc.model.RpcResponse;

import java.util.Map;

/**
 * @author xlm
 * 2024/7/15 下午1:50
 * 容错机制，是在发送请求失败时处理，因此返回值是RpcResponse，参数是山下文信息和异常信息
 */
public interface TolerantStrategy {
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
