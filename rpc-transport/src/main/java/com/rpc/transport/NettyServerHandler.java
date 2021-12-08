package com.rpc.transport;

import com.rpc.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: com.rpc.transport
 * @ClassName: NettyServerHandler
 * @Author: 19770
 * @Description:
 * @Date: 2021/12/5 15:36
 * @Version: 1.0
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<Request> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }


}
