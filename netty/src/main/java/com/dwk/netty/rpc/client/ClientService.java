package com.dwk.netty.rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 客户端,服务消费者
 */
public class ClientService {

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 6666;

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    private ClientHandler clientHandler;

    /**
     * 动态代理
     *
     * @param clazz    要被代理的类
     * @param protocol 和服务提供方约定好的协议头
     * @return
     */
    public Object getBean(final Class<?> clazz, final String protocol) {
        /*
        loader : 类加载器
        interfaces : 要实现的代理类列表
        h : 处理程序
         */
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{clazz},
                (proxy, method, args) -> {
                    if (clientHandler == null) {
                        initClient(HOST, PORT);
                    }
                    // args: 客户端调用服务提供者时传的参数
                    clientHandler.setParam(protocol + args[0]);
                    return executor.submit(clientHandler).get();
                });
    }

    /**
     * 客户端初始化
     *
     * @param host
     * @param port
     */
    private void initClient(String host, int port) {
        clientHandler = new ClientHandler();
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(clientHandler);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("客户端启动成功!");
                    }
                }
            });
            //channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

}

class ClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    //channelhandler上下文
    private ChannelHandlerContext context;
    //服务提供方返回结果
    private String result;
    //传参
    private String param;

    public void setParam(String param) {
        this.param = param;
    }

    /**
     * 连接成功后被调用且只被调用一次
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    /**
     * 接收服务提供者返回的数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        //唤醒call()
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 被代理对象调用，发送数据给服务端(服务提供者)
     *
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        context.channel().writeAndFlush(param);
        System.out.println("客户端成功发送消息：" + param);
        wait();
        return result;
    }
}
