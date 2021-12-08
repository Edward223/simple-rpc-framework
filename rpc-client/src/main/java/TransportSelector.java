
import com.rpc.Peer;
import com.rpc.transport.TransportClient;


import java.util.List;


/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/11/29
 * @修改人和其它信息
 */
public interface TransportSelector {
    /**
     *@描述 初始化
     *@参数  可以连接的端点信息
     *@返回值
     *@创建人  xiong Nie
     *@创建时间  2021/12/2
     */
    void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz);

    TransportClient select();

    void release(TransportClient client);

    void close();


}
