package com.dwk.netty.protobuf.protodemo;

import com.dwk.netty.protobuf.protobean.StudentBean;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 与服务端使用proto交互
 */
public class ProtoClientDemo {

    public static void main(String[] args) {
        new ProtoClientDemo().start();
    }

    public void start() {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 添加编码器
                            pipeline.addLast("protoEncoder",new ProtobufEncoder());
                            pipeline.addLast("clientHandler",new ClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",6666).sync();
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("客户端启动成功!");
                    }
                }
            });
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

    class ClientHandler extends ChannelInboundHandlerAdapter{

        @Override
        public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
            // 发送一个proto对象给服务端
            StudentBean.StudentProto studentProto = StudentBean
                    .StudentProto
                    .newBuilder()
                    .setId(1)
                    .setName("Ronnie O'Sullivan")
                    .build();
            channelHandlerContext.writeAndFlush(studentProto);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf msg1 = (ByteBuf) msg;
            System.out.println("收到消息：" + msg1.toString(CharsetUtil.UTF_8));
        }
    }

}
