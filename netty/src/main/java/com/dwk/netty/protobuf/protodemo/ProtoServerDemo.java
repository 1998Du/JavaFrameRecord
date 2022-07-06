package com.dwk.netty.protobuf.protodemo;

import com.dwk.netty.protobuf.protobean.StudentBean;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 接收客户端发送的proto对象
 */
public class ProtoServerDemo {

    public static void main(String[] args) {
        new ProtoServerDemo().start();
    }

    public void start(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 添加对proto对象的解码器
                            pipeline.addLast("protoDecoder",new ProtobufDecoder(StudentBean.StudentProto.getDefaultInstance()));
                            pipeline.addLast("serverhandler",new ServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(6666).sync();
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("服务端启动成功!");
                    }
                }
            });
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    class ServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof StudentBean.StudentProto){
                System.out.println("客户端发来消息：" + msg);
                StudentBean.StudentProto student = (StudentBean.StudentProto) msg;
                System.out.println(student.getId());
                System.out.println(student.getName());
                ctx.writeAndFlush("服务器收到了你发的消息，并进行了处理！" + ctx.channel().remoteAddress());
            }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(Unpooled.copiedBuffer("服务器收到了你发的消息，并进行了处理！" + ctx.channel().remoteAddress(), CharsetUtil.UTF_8));
        }
    }

}
