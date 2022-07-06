package com.dwk.netty.rpc.service;

import com.dwk.netty.rpc.provider.ProviderService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 远程服务端，服务提供者
 */
public class RemoteService {

    public static void start(String host,int port){
        startServer0(host,port);
    }

    private static void startServer0(String host,int port){
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
                            //解码
                            pipeline.addLast(new StringDecoder());
                            //编码
                            pipeline.addLast(new StringEncoder());
                            //业务处理
                            pipeline.addLast(new ServerHandler());

                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(host, port).sync();
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("服务提供方启动成功!");
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

}

/**
 * 服务端处理器，服务端接收客户端发送的消息，因此处理器为入站处理
 */
class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final String PROTOCOL = "/";

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端"+ctx.channel().remoteAddress()+"连接");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务端收到消息：" + msg.toString());
        String result;
        //获取客户端发送的消息，并调用服务  以/开头的消息才处理
        if (msg.toString().startsWith(PROTOCOL)){
            System.out.println("接收到客户端消息：" + msg + "符合处理规则，开始处理...");
            //根据客户端的请求进行相关的操作
            result = new ProviderService().connect((String) msg);
            System.out.println("客户端消息处理完成，结果返回客户端！");
            //将处理结果返回给客户端
            ctx.writeAndFlush(result);
        }else{
            result = "接收到客户端消息:" + msg + "不符合处理规则！";
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
