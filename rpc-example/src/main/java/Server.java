import com.rpc.server.NettyRpcServer;
import com.rpc.server.RpcServer;
import com.rpc.server.RpcServerConfig;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/2
 * @修改人和其它信息
 */
public class Server {

    public static void main(String[] args) {
//        RpcServer rpcServer=new RpcServer(new RpcServerConfig());
//        rpcServer.register(CalculateService.class,new Calculate());
//        rpcServer.start();

        new NettyRpcServer().start();
    }
}
