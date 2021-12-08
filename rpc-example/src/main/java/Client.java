/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/2
 * @修改人和其它信息
 */
public class Client {
    public static void main(String[] args) throws Exception {
//        RpcClient client=new RpcClient();
//        CalculateService calculateService=client.getProxy(CalculateService.class);
//        System.out.println(calculateService.add(2,2));
//        System.out.println(calculateService.def(10,3));
        String host="127.0.0.1";
        int port=3000;

        new NettyRpcClient(host,port).start();
    }
}
