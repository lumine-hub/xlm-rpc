package com.xlm.rpc.springbootstarter.annotation;

import com.xlm.rpc.springbootstarter.bootstrap.RpcConsumerBootstrap;
import com.xlm.rpc.springbootstarter.bootstrap.RpcInitBootstrap;
import com.xlm.rpc.springbootstarter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xlm
 * 2024/7/15 下午4:07
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {
    /**
     * 需要启动server
     */
    boolean needServer() default true;
}
