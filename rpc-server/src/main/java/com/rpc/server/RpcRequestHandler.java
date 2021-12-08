package com.rpc.server;

import com.rpc.RpcRequest;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: com.rpc.server
 * @ClassName: RpcRequestHandler
 * @Author: Tang
 * @Description:
 * @Date: 2021/12/8 15:22
 * @Version: 1.0
 */
public class RpcRequestHandler {

    public Object handle(RpcRequest rpcRequest){
        return new String("hello! I'm Server");
    }
}
