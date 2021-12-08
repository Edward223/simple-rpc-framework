package com.rpc.serialize;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.rpc.RpcRequest;
import com.rpc.RpcResponse;
import com.rpc.common.exception.SerializeException;
import lombok.extern.slf4j.Slf4j;

import javax.sql.rowset.serial.SerialException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: com.rpc.serialize
 * @ClassName: KryoSerialize
 * @Author: Tang
 * @Description:
 * @Date: 2021/12/5 22:02
 * @Version: 1.0
 */
@Slf4j
public class KryoSerialize implements Serializer {

    private static final ThreadLocal<Kryo> kryoThreadLocal=ThreadLocal.withInitial(()->{
        Kryo kryo=new Kryo();
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) {
        try (
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                Output output=new Output(byteArrayOutputStream)){

            Kryo kryo= kryoThreadLocal.get();

            kryo.writeObject(output,obj);
            kryoThreadLocal.remove();
            return output.toBytes();
        } catch (Exception e) {
            log.warn(e.getMessage(),e);
            throw new SerializeException("Serialization failed");
        }

    }

    /**
     *
     * @param bytes 要反序列化的字节数组
     * @param clazz 要反序列化成的目标类
     * @param <T>
     * @return  反序列化的目标类实例
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (
                ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
                Input input=new Input(byteArrayInputStream)){

            Kryo kryo= kryoThreadLocal.get();

            Object o=kryo.readObject(input,clazz);
            kryoThreadLocal.remove();
            return clazz.cast(o);
        } catch (Exception e) {
            log.warn(e.getMessage(),e);
            throw new SerializeException("Deserialization failed");
        }

    }
}
