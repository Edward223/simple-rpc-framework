package com.rpc.transport;

import com.rpc.Request;
import com.rpc.Response;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: com.rpc.transport
 * @ClassName: RequestHandlerImpl
 * @Author: Tang
 * @Description:
 * @Date: 2021/12/5 16:02
 * @Version: 1.0
 */
public class RequestHandlerImpl implements RequestHandler {
    @Override
    public void onRequest(InputStream recive, OutputStream toResp) {
//        Response response=new Response();
//        try {
//            byte[] inBytes = IOUtils.readFully(recive, recive.available());
//            Request request = decoder.decode(inBytes, Request.class);
//            log.info("Request:{}", request);
//            Object ret = serviceInvoker.invoke(serviceManager.lookup(request), request);
//            response.setData(ret);
//        } catch (Exception e) {
//            log.warn(e.getMessage(),e);
//            response.setMessage("RpcServer got error:"
//                    +e.getClass().getName()+
//                    ":"+e.getMessage());
//        }
//        finally {
//            byte[] outBytes=encoder.encode(response);
//            try {
//                toResp.write(outBytes);
//                log.info("Reponse Client");
//            } catch (IOException e) {
//                log.warn(e.getMessage(),e);
//            }
//        }
    }
}
