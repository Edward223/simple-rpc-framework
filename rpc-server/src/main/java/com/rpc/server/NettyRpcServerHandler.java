package com.rpc.server;

import com.rpc.RpcConstants;
import com.rpc.RpcMessage;
import com.rpc.RpcRequest;
import com.rpc.RpcResponse;
import com.rpc.common.enumeration.RpcResponseCodeEnum;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: com.rpc.server
 * @ClassName: NettyRpcServerHandler
 * @Author: Tang
 * @Description:
 * @Date: 2021/12/8 14:50
 * @Version: 1.0
 */
@Slf4j
public class NettyRpcServerHandler extends ChannelInboundHandlerAdapter {
    private final RpcRequestHandler rpcRequestHandler;

    public NettyRpcServerHandler(){
        this.rpcRequestHandler=new RpcRequestHandler();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if(msg instanceof RpcMessage){
                log.info("server receive msg: [{}] ", msg);
                byte messageType=((RpcMessage) msg).getMessageType();
                RpcRequest rpcRequest=(RpcRequest) ((RpcMessage) msg).getData();
                //调用函数
                Object result=rpcRequestHandler.handle(rpcRequest);
                log.info(String.format("server get result: %s", result.toString()));
                RpcMessage rpcMessage=new RpcMessage();
                rpcMessage.setMessageType(RpcConstants.RESPONSE_TYPE);
                if(ctx.channel().isActive()&&ctx.channel().isWritable()){
                    RpcResponse<Object> rpcResponse=RpcResponse.success(result,rpcRequest.getRequestId());
                    rpcMessage.setData(rpcResponse);
                }else{
                    RpcResponse<Object> rpcResponse=RpcResponse.fail(RpcResponseCodeEnum.FAIL);
                    rpcMessage.setData(rpcResponse);
                    log.error("not writable now, message dropped");
                }
                ctx.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
