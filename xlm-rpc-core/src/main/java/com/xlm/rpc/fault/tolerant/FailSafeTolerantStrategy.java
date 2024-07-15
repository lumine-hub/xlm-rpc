package com.xlm.rpc.fault.tolerant;

import com.xlm.rpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author xlm
 * 2024/7/15 下午1:58
 *
 * 静默处理
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常", e);
        return new  RpcResponse();
    }
}
