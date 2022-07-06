package com.dwk.netty.longconnection;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.time.LocalDateTime;

/**
 * Netty 使用 WebSocket实现http长连接
 */
public class LongConnectionDemo {

}

/**
 * 服务端启动器
 */
class ServerStart{

    private static final int PORT = 6666;

    public static void main(String[] args) {
        new Server(PORT).start();
    }
}

/**
 * 服务端
 */
class Server{

    private int PORT;

    public Server(int PORT) {
        this.PORT = PORT;
    }

    public void start(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 基于http协议，使用http的编码解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 以块方式写，添加ChunkedwriteHandler处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            /*
                            http数据在传输过程中是分段的，HttpObjectAggregator可以将多个段聚合
                            当浏览器发送大量数据时就会发出多次http请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(65536));
                            /*
                            对于Websocket的数据是以帧的形式传递的
                            浏览器请求时：ws://localhost:6666/test
                            WebSocketServerProtocolHandler 核心功能：将http协议升级为ws协议，保持长链接
                            通过状态码101切换从http协议升级成websocket协议
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/test"));
                            // 自定义handler，处理业务逻辑
                            pipeline.addLast(new ServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
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
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 服务端处理器
     * TextWebSocketFrame : 表示一个文本帧
     */
    class ServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

        /**
         * 客户端连接后第一个调用的方法
         * @param ctx
         * @throws Exception
         */
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            //  唯一值
            System.out.println(ctx.channel().remoteAddress() + "  客户端连接了,id为：" + ctx.channel().id().asLongText());
        }

        /**
         * 数据交互
         * @param channelHandlerContext
         * @param textWebSocketFrame
         * @throws Exception
         */
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
            System.out.println("服务端接收到消息长度:" + textWebSocketFrame.text().length());
            System.out.println("服务端收到消息:" + textWebSocketFrame.text());
            // 回复消息
            channelHandlerContext.writeAndFlush(new TextWebSocketFrame("服务器时间：" + LocalDateTime.now() + textWebSocketFrame.text()));
        }

        /**
         * 连接关闭
         */
        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            System.out.println(ctx.channel().remoteAddress() + "  客户端关闭了,id为：" + ctx.channel().id().asLongText());
        }

        /**
         * 异常处理
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

}