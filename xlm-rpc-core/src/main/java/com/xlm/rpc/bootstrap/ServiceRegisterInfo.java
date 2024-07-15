package com.xlm.rpc.bootstrap;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xlm
 * 2024/7/15 下午3:08
 */
@Data
@AllArgsConstructor
public class ServiceRegisterInfo<T> {
    private String serviceName;

    private Class<? extends T> implClass;
}
