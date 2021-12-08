package com.rpc.codec;

import com.rpc.RpcConstants;
import com.rpc.RpcMessage;
import com.rpc.RpcRequest;
import com.rpc.RpcResponse;
import com.rpc.serialize.KryoSerialize;
import com.rpc.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;


//import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
//import static io.netty.util.internal.StringUtil.NEWLINE;


import java.util.Arrays;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: com.rpc.codec
 * @ClassName: KryoDecoder
 * @Author: Tang
 * @Description:
 * @Date: 2021/12/7 13:17
 * @Version: 1.0
 */
@Slf4j
public class KryoDecoder extends LengthFieldBasedFrameDecoder {
    public KryoDecoder() {
        this(RpcConstants.MAX_FRAME_LENGTH, 5, 4,-9,0);
    }

    public KryoDecoder(int maxFrameLength,
                       int lengthFieldOffset,
                       int lengthFieldLength,
                       int lengthAdjustment,
                       int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
    //调试工具
//    private static void log(ByteBuf buffer){
//        int length=buffer.readableBytes();
//        int rows=length/16+(length%15==0?0:1)+4;
//        StringBuilder buf=new StringBuilder(rows*80*2)
//                .append("read index:").append(buffer.readerIndex())
//                .append(" write index:").append(buffer.writerIndex())
//                .append(" capacity:").append(buffer.capacity())
//                .append(NEWLINE);
//        appendPrettyHexDump(buf,buffer);
//        System.out.println(buf.toString());
//    }
    @Override
    protected Object decode(ChannelHandlerContext ctx,
                            ByteBuf in) throws Exception {

        //调试
        //log(in);

        Object decoded=super.decode(ctx,in);
        if(decoded instanceof ByteBuf){
            ByteBuf frame=(ByteBuf) decoded;
            if(frame.readableBytes()>=RpcConstants.TOTAL_LENGTH){
                try{
                    return decodeFrame(frame);
                }catch (Exception e){
                    log.error("Decode frame error!", e);
                    throw e;
                }finally {
                    frame.release();
                }
            }
        }

        return decoded;
    }

    private  Object decodeFrame(ByteBuf in){
        checkMagicNumber(in);
        checkVersion(in);
        int fullLength=in.readInt();
        byte messageType=in.readByte();
        int requestId=in.readInt();

        RpcMessage rpcMessage=RpcMessage.builder()
                .requestId(requestId)
                .messageType(messageType).build();

        int bodyLength=fullLength-RpcConstants.HEAD_LENGTH;
        if(bodyLength>0){
            byte[] bs=new byte[bodyLength];
            in.readBytes(bs);
            Serializer serializer=new KryoSerialize();
            if (messageType==RpcConstants.REQUEST_TYPE){
                RpcRequest tmpValue=serializer.deserialize(bs,RpcRequest.class);
                rpcMessage.setData(tmpValue);
            }else{
                RpcResponse tmpValue=serializer.deserialize(bs,RpcResponse.class);
                rpcMessage.setData(tmpValue);
            }
        }

        return rpcMessage;
    }

    private void checkVersion(ByteBuf in) {
        // read the version and compare
        byte version = in.readByte();
        if (version != RpcConstants.VERSION) {
            throw new RuntimeException("version isn't compatible" + version);
        }
    }

    private void checkMagicNumber(ByteBuf in) {
        // read the first 4 bit, which is the magic number, and compare
        int len = RpcConstants.MAGIC_NUMBER.length;
        byte[] tmp = new byte[len];
        in.readBytes(tmp);
        for (int i = 0; i < len; i++) {
            if (tmp[i] != RpcConstants.MAGIC_NUMBER[i]) {
                throw new IllegalArgumentException("Unknown magic code: " + Arrays.toString(tmp));
            }
        }
    }
}
