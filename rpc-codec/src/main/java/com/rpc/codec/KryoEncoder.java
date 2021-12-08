package com.rpc.codec;

import com.rpc.RpcConstants;
import com.rpc.RpcMessage;
import com.rpc.serialize.KryoSerialize;
import com.rpc.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * @ProjectName: simple-rpc-framework
 * @Package: com.rpc.codec
 * @ClassName: KryoEncoder
 * @Author: Tang
 * @Description: netty框架下使用kryo实现编码器
 * @Date: 2021/12/5 20:39
 * @Version: 1.0
 */
@Slf4j
public class KryoEncoder extends MessageToByteEncoder<RpcMessage> {
    private static final AtomicInteger ATOMIC_INTEGER=new AtomicInteger(0);


//    private static void show(ByteBuf buffer){
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
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          RpcMessage rpcMessage,
                          ByteBuf out) {
        try {
            out.writeBytes(RpcConstants.MAGIC_NUMBER);
            out.writeByte(RpcConstants.VERSION);
            //跳过并留下full length的空间
            out.writerIndex(out.writerIndex()+4);

            out.writeByte(rpcMessage.getMessageType());
//        out.writeByte(rpcMessage.getCodec());
//        写入压缩类型
//        out.writeByte((byte)0x01);
            //写入requestID

            out.writeInt(ATOMIC_INTEGER.incrementAndGet());

            byte[] bodyBytes=null;
            int fullLength=RpcConstants.HEAD_LENGTH;
            //对body进行序列化
            Serializer serializer=new KryoSerialize();
            bodyBytes=serializer.serialize(rpcMessage.getData());

            fullLength+=bodyBytes.length;

            if(bodyBytes!=null)
                out.writeBytes(bodyBytes);

            //调试
            //show(out);

            int writeIndex= out.writerIndex();
            out.writerIndex(writeIndex-fullLength+RpcConstants.MAGIC_NUMBER.length+1);
            out.writeInt(fullLength);
            out.writerIndex(writeIndex);

            //show(out);
        } catch (Exception e) {
            log.error("Encode request error!", e);
        }
    }
}
