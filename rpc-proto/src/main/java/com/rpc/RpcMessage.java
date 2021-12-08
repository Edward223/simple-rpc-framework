package com.rpc;

import lombok.*;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: com.rpc
 * @ClassName: RpcMessage
 * @Author: Tang
 * @Description:
 * @Date: 2021/12/6 12:37
 * @Version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RpcMessage {
    //rpc message type
    private byte messageType;
    //serialization type
//    private byte codec;
    //compress type
//    private byte compress;
    //request id
    private int requestId;
    //request data
    private Object data;
}
