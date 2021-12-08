import com.rpc.codec.KryoDecoder;
import com.rpc.codec.KryoEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;

import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: PACKAGE_NAME
 * @ClassName: NettyRpcClient
 * @Author: Tang
 * @Description:
 * @Date: 2021/12/8 18:02
 * @Version: 1.0
 */
@Slf4j
@AllArgsConstructor
public class NettyRpcClient {


    private final String host;
    private final int port;



    public void start() throws Exception{
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup(1);
        Bootstrap bootstrap=new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    //.handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .remoteAddress(this.host,this.port)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p=socketChannel.pipeline();
                            p.addLast(new IdleStateHandler(0,
                                    5,
                                    0,
                                    TimeUnit.SECONDS));
                            p.addLast(new KryoEncoder());
                            p.addLast(new KryoDecoder());
                            p.addLast(new NettyRpcClientHandler());
                        }
                    });
            ChannelFuture f=bootstrap.connect().sync();
            f.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
