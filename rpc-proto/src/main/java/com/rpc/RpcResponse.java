package com.rpc;

import com.rpc.common.enumeration.RpcResponseCodeEnum;
import lombok.*;

import java.io.Serializable;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: com.rpc
 * @ClassName: RpcResponse
 * @Author: Tang
 * @Description:
 * @Date: 2021/12/5 22:09
 * @Version: 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class RpcResponse<T> implements Serializable {
    private static final long serialVersionUID=1L;
    private String requestId;

    private Integer code;

    private String message;

    private T data;

    public static <T> RpcResponse<T> success(T data,String requestId){
        RpcResponse<T> response=new RpcResponse<>();
        response.setCode(RpcResponseCodeEnum.SUCCESS.getCode());
        response.setRequestId(requestId);
        response.setMessage(RpcResponseCodeEnum.SUCCESS.getMessage());

        if(data!=null)
            response.setData(data);

        return response;
    }

    public static <T> RpcResponse<T> fail(RpcResponseCodeEnum rpcResponseCodeEnum){
        RpcResponse<T> response=new RpcResponse<>();
        response.setCode(RpcResponseCodeEnum.FAIL.getCode());
        response.setMessage(RpcResponseCodeEnum.FAIL.getMessage());

        return response;
    }
}
