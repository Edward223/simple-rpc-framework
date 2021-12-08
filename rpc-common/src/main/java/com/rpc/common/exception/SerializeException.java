package com.rpc.common.exception;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: com.rpc.common.exception
 * @ClassName: SerializeException
 * @Author: Tang
 * @Description:
 * @Date: 2021/12/6 11:02
 * @Version: 1.0
 */
public class SerializeException extends RuntimeException {
    public SerializeException(String message) {
        super(message);
    }
}
