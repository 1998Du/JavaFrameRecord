package com.dwk.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.SocketAddress;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 群聊系统
 */
public class GroupChatDemo {

}

/**
 * 服务端启动器
 */
class ServerApp{
    private static final int PORT = 6666;

    public static void main(String[] args) {
        Logger.getLogger("io.netty").setLevel(Level.OFF);
        new Server(PORT).start();
    }
}

/**
 * 客户端启动器
 */
class ClientApp{

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 6666;

    public static void main(String[] args) {
        Logger.getLogger("io.netty").setLevel(Level.OFF);
        new Client(HOST,PORT).start();
    }
}

/**
 * 服务端
 */
class Server{

    private int PORT;

    public Server(int port) {
        this.PORT = port;
    }

    public void start(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decode", new StringDecoder());
                            pipeline.addLast("encode", new StringEncoder());
                            pipeline.addLast("GroupChatServerHandler", new GroupChatServerHandler());
                        }
                    });
            ChannelFuture bind = serverBootstrap.bind(PORT).sync();
            bind.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("服务端启动成功!");
                    }
                }
            });
            bind.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 服务端处理器
     */
    static class GroupChatServerHandler extends SimpleChannelInboundHandler<String>{

        private static final String TIP = "==========>";

        /*
        定义一个channel组，管理所有的channel
        GlobalEventExecutor.INSTANCE 是全局的事件执行器  单例！
         */
        private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

        /**
         * 连接建立后第一个执行的方法
         * @param ctx
         * @throws Exception
         */
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            // 将当前Channel加入到channelGroup
            Channel channel = ctx.channel();
            /*
             将该客户端连接消息推送给所有客户端
             writeAndFlush 会将channelGroup中所有的channel遍历并发送消息,不需要自己遍历
             */
            channelGroup.writeAndFlush(TIP + channel.remoteAddress() + "连接服务端成功!");
            channelGroup.add(channel);
        }

        /**
         * 表示channel处于活跃状态
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            Channel channel = ctx.channel();
            SocketAddress socketAddress = channel.remoteAddress();
            System.out.println(socketAddress + "上线!");
        }

        /**
         * 表示channel处于非活跃状态
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            Channel channel = ctx.channel();
            SocketAddress socketAddress = channel.remoteAddress();
            System.out.println(socketAddress + "离线!");
        }

        /**
         * 断开连接,将客户端断开消息推送给所有连接的客户端
         * 此方法执行后断开连接的客户端会被自动移除出channelGroup
         * @param ctx
         * @throws Exception
         */
        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            Channel channel = ctx.channel();
            SocketAddress socketAddress = channel.remoteAddress();
            channelGroup.writeAndFlush(TIP + socketAddress + " 断开服务器!");
        }

        /**
         * 数据处理
         * @param channelHandlerContext
         * @param msg
         * @throws Exception
         */
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
            Channel channel = channelHandlerContext.channel();
            // 遍历channelGroup 不同的客户端回送不同的消息
            channelGroup.forEach(item -> {
                // 非当前channel,即其他客户端  转发消息
                if (channel != item){
                    item.writeAndFlush(TIP + channel.remoteAddress() + "发送了消息：" + msg);
                }else{
                    // 当前channel 即当前客户端
                    item.writeAndFlush(TIP + "我的消息：" + msg);
                }
            });
        }

        /**
         * 异常处理
         * @param ctx
         * @param cause
         * @throws Exception
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }

}

/**
 * 客户端
 */
class Client{

    private String HOST;

    private int PORT;

    public Client(String HOST, int PORT) {
        this.HOST = HOST;
        this.PORT = PORT;
    }

    public void start(){
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast("GroupChatClientHandler",new ClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
            Channel channel = channelFuture.channel();
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("客户端:" + channel.remoteAddress() + "启动成功!");
                    }
                }
            });
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String msg = scanner.nextLine();
                // 通过channel发送到服务端
                channel.writeAndFlush(msg);
            }
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            eventExecutors.shutdownGracefully();
        }
    }

    /**
     * 客户端处理器
     */
    class ClientHandler extends SimpleChannelInboundHandler<String>{

        /**
         * 处理和服务器连接的channel中的数据
         * @param channelHandlerContext
         * @param msg
         * @throws Exception
         */
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
            System.out.println(msg);
        }
    }

}