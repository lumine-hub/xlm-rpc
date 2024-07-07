package com.xlm.rpc.serializer;

import com.xlm.rpc.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 序列化器对象是可以复用的，没必要每次执行序列化操作前都创建一个新的对象。
 * 所以我们可以使用设计模式中的 工厂模式 + 单例模式 来简化创建和获取序列化器对象的操作。
 */
public class SerializerFactory {

    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }

}
