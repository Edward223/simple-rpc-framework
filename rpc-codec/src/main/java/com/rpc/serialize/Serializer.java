package com.rpc.serialize;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: com.rpc.serialize
 * @ClassName: Serializer
 * @Author: Tang
 * @Description:
 * @Date: 2021/12/5 22:03
 * @Version: 1.0
 */
public interface Serializer {
    /**
     *
     * @param obj 将要序列化的对象
     * @return  返回字节数组
     */
    byte[] serialize(Object obj);


    /**
     *
     * @param bytes 要反序列化的字节数组
     * @param clazz 要反序列化成的目标类
     * @param <T>  类的类型。举个例子,  {@code String.class} 的类型是 {@code Class<String>}.
     *      *              如果不知道类的类型的话，使用 {@code Class<?>}
     * @return  反序列化后的对象
     */
    <T> T deserialize(byte[] bytes,Class<T> clazz);
}
