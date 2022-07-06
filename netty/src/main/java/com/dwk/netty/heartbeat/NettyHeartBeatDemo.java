package com.dwk.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Netty心跳机制
 * 服务端和客户端之间通信检测
 */
public class NettyHeartBeatDemo {

}

/**
 * 服务端启动器
 */
class ServerApp{

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
                    .handler(new LoggingHandler(LogLevel.INFO)) // 在bossGroup增加日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 加入netty提供的IdleStateHandler
                            /*
                            IdleStateHandler : netty提供的处理空闲状态的处理器
                                参数:
                                readerIdleTime : 表示多长时间没有读，就会发送一个心跳检测包检测连接是否正常
                                writerIdleTime : 表示多长时间没有写，就会发送一个心跳检测包检测连接是否正常
                                allIdleTime : 表示多长时间既没有读也没有写，就会发送一个心跳检测包检测连接是否正常
                                unit : 时间单位
                             当IdleStateHandler触发后就会传递给管道的下一个handler去处理，通过调用下一个handler的userEventTriggered方法
                             */
                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            // 加入一个对空闲检测进一步处理的handler(自定义)
                            pipeline.addLast(new HeartBeatHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    /**
     * 心跳处理器
     */
    class HeartBeatHandler extends ChannelInboundHandlerAdapter {

        /**
         * 对空闲检测的进一步处理
         * @param ctx
         * @param evt 事件
         * @throws Exception
         */
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if(evt instanceof IdleStateEvent){
                // evt 转 IdleStateEvent
                IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
                // 判断 idleStateEvent 类型
                String freeType = null;
                switch (idleStateEvent.state()){
                    // 读 空闲
                    case READER_IDLE :
                        freeType = "  " + LocalDateTime.now() +  "  读-空闲";
                        break;
                    // 写 空闲
                    case WRITER_IDLE:
                        freeType = "  " + LocalDateTime.now() + "  写-空闲";
                        break;
                    // 读写 空闲
                    case ALL_IDLE:
                        freeType = "  " + LocalDateTime.now() + "  读写-空闲";
                        break;
                }
                System.out.println(ctx.channel().remoteAddress() + freeType);
            }
        }

    }

}

