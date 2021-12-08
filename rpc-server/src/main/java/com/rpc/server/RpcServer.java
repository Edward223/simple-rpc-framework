package com.rpc.server;

import com.rpc.Request;
import com.rpc.Response;
import com.rpc.codec.Decoder;
import com.rpc.codec.Encoder;
import com.rpc.common.utils.ReflectionUtils;
import com.rpc.transport.RequestHandler;
import com.rpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/2
 * @修改人和其它信息
 */
@Slf4j
public class RpcServer {
    private RpcServerConfig config;
    private TransportServer transportServer;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RpcServer(RpcServerConfig config) {
        this.config = config;
        this.transportServer = ReflectionUtils.newInstance(config.getTransportClass());
        this.transportServer.init(3000, handler);
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDeocderClass());
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();
    }

    public void start() {
        this.transportServer.start();
    }

    public void register(Class clazz,Object bean){
        serviceManager.register(clazz,bean);

    }
    public void stop() {
        this.transportServer.stop();
    }

    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream recive, OutputStream toResp) {
            Response response=new Response();
            try {
                byte[] inBytes = IOUtils.readFully(recive, recive.available());
                Request request = decoder.decode(inBytes, Request.class);
                log.info("Request:{}", request);
                Object ret = serviceInvoker.invoke(serviceManager.lookup(request), request);
                response.setData(ret);
            } catch (Exception e) {
                log.warn(e.getMessage(),e);
                response.setMessage("RpcServer got error:"
                +e.getClass().getName()+
                        ":"+e.getMessage());
            }
            finally {
                byte[] outBytes=encoder.encode(response);
                try {
                    toResp.write(outBytes);
                    log.info("Reponse Client");
                } catch (IOException e) {
                    log.warn(e.getMessage(),e);
                }
            }

        }
    };

}
