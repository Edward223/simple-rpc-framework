package com.rpc.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: com.rpc.common.enumeration
 * @ClassName: RpcResponseCode
 * @Author: Tang
 * @Description:
 * @Date: 2021/12/5 22:29
 * @Version: 1.0
 */

@AllArgsConstructor
@Getter
@ToString
public enum RpcResponseCodeEnum {
    SUCCESS(200, "The remote call is successful"),
    FAIL(500, "The remote call is fail");
    private final int code;

    private final String message;
}
