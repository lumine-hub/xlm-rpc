package com.xlm.rpc.springbootstarter.annotation;

import com.xlm.rpc.constant.RpcConstant;
import com.xlm.rpc.fault.retry.RetryStrategyKeys;
import com.xlm.rpc.fault.tolerant.TolerantStrategyKeys;
import com.xlm.rpc.loadbalancer.LoadBalancerKeys;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xlm
 * 2024/7/15 下午4:07
 *
 * consumer用来注入服务
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcReference {
    /**
     * 服务接口类
     */
    Class<?> interfaceClass() default void.class;

    /**
     * 版本
     */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 负载均衡器
     */
    String loadBalancer() default LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     */
    String retryStrategy() default RetryStrategyKeys.NO;

    /**
     * 容错策略
     */
    String tolerantStrategy() default TolerantStrategyKeys.FAIL_FAST;

    /**
     * 模拟调用
     */
    boolean mock() default false;
}
