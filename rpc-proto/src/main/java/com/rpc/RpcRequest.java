package com.rpc;

import lombok.*;

import java.io.Serializable;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: com.rpc
 * @ClassName: RpcRequest
 * @Author: Tang
 * @Description:
 * @Date: 2021/12/5 20:54
 * @Version: 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class RpcRequest implements Serializable {
    private static final long serialVersionUID=1L;
    private String requestId;//请求Id
    private String interfaceName;//需要调用的接口的名字
    private String methodName;//需要调用的方法名
    private Object[] parameters;//方法需要参数的实体
    private Class<?>[] paramTypes;//方法需要参数的类
    private String version;//
    private String group;//



}
