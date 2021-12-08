import com.rpc.RpcConstants;
import com.rpc.RpcMessage;
import com.rpc.RpcRequest;
import com.rpc.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: PACKAGE_NAME
 * @ClassName: NettyRpcClientHandler
 * @Author: Tang
 * @Description:
 * @Date: 2021/12/8 18:12
 * @Version: 1.0
 */
@Slf4j
public class NettyRpcClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg=new String("Hello,I'm client");
        RpcRequest request=new RpcRequest();
        request.setMethodName(msg);
        RpcMessage message=RpcMessage.builder()
                .messageType(RpcConstants.REQUEST_TYPE)
                .data(request).build();
        ctx.writeAndFlush(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("client catch exception：", cause);
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            log.info("client receive msg: [{}]", msg);
            if(msg instanceof RpcMessage){
                RpcMessage tmp=(RpcMessage) msg;
                if(tmp.getMessageType()== RpcConstants.RESPONSE_TYPE){
                    RpcResponse<Object> rpcResponse=(RpcResponse<Object>) tmp.getData();
                    System.out.println("Client received: " + rpcResponse.getData().toString());
                }

            }
        }finally {

        }
    }
}
