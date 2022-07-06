package com.dwk.netty.handlerchain;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * Handler链调用机制 DEMO    byte和long编码解码案例
 */
public class HandlerChainDemo {

}

/**
 * 服务端
 */
class Server{

    public static void main(String[] args) {

    }

}

/**
 * 服务端解码器
 */
class ServerDecoder extends ByteToMessageDecoder {

    /**
     *
     * @param channelHandlerContext  上下文
     * @param byteBuf 入站的buf
     * @param list 将解码后的数据传递给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // Long是8个字节，需要有8个字节才可以转
        if (byteBuf.readableBytes() >= 8){
            list.add(byteBuf.readLong());
        }
    }
}

/**
 * 服务端处理器
 */
class ServerHandler extends SimpleChannelInboundHandler<Long>{

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("客户端发送消息：" + aLong);
    }
}

/**
 * 客户端
 */
class Client{

    public static void main(String[] args) {

    }

}

/**
 * 客户端编码器
 */
class ClientEncoder extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long o, ByteBuf byteBuf) throws Exception {
        byteBuf.writeLong(o);
    }
}
