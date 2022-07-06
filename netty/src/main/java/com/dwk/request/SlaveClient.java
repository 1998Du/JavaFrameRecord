package com.dwk.request;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;

/**
 * 客户端
 */
public class SlaveClient {

    //创建读字节缓冲区，大小为4个字节   大端
    private final ByteBuffer readBuf = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
    //创建写字节缓冲区，大小为4个字节   大端
    private final ByteBuffer writeBuf = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
    //创建套接字通道
    private SocketChannel channel;

    /**发送*/
    public void send(byte[] body){
        try {
            //创建客户端通道
            channel = SocketChannel.open();
            channel.socket().setSoTimeout(10000);
            channel.connect(new InetSocketAddress("",8080));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
