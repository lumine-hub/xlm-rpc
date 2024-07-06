package com.xlm.rpc.serializer;

import java.io.IOException;

public interface Serializer {

    /**
     * 序列化 Object -> byte[]
     */
    <T> byte[] serialize(T object) throws IOException;

    /**
     * 反序列化 byte[] -> object
     */
    <T> T deserialize(byte[] bytes, Class<T> cls) throws IOException;
}
