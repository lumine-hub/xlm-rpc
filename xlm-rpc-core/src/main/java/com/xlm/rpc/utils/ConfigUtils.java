package com.xlm.rpc.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

public class ConfigUtils {

    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }
    public static <T> T loadConfig(Class<T> configClass, String prefix, String env) {
        StringBuilder path = new StringBuilder("application");
        if (StrUtil.isNotBlank(env)) {
            path.append("-").append(env);
        }
        path.append(".properties");
        Props props = new Props(path.toString());
        return props.toBean(configClass, prefix);
    }

}