package com.dwk.netty.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class NettyServerDemo {

    public static void main(String[] args) {
        new NettyServerDemo().nettyServer();
    }

    /**
     * 服务端
     */
    public void nettyServer() {
        /*
        1、创建两个线程组 bossGroup和workerGroup
        2、bossGroup只是处理连接请求  真正和客户端业务处理会交给workerGroup完成
        3、bossGroup 和 workerGroup都是无限循环
        4、bossGroup 和 workerGroup含有的子线程(NioEventLoop)的个数 默认 = 实现cpu核数 * 2
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务端启动配置
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup) //设置线程组
                    .channel(NioServerSocketChannel.class) //使用NioSocketChannel 作为服务端通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到的连接个数 (设置全连接队列的大小)
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                    .handler(null) //handler方法中注册的是bossGroup处理器  childHandler方法中注册的是workerGroup处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道测试对象
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //具体处理客户端消息的处理器 channelHandlers
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    }); //给workerGroup 的EventLoop 对应的管道设置处理器

            System.out.println("服务端初始化完成");

            //绑定端口 并设置同步
            ChannelFuture channelFuture = serverBootstrap.bind(6666).sync();

            //给bind方法添加监听器
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()){
                        System.out.println("监听端口成功");
                    }else{
                        System.out.println("监听端口失败");
                    }
                }
            });

            //对关闭通道进行监听,涉及到异步模型
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            //关闭线程组  优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    /**
     * 适配器模式
     * 自定义服务端处理器   需要继承netty规定好的适配器
     */
    class NettyServerHandler extends ChannelInboundHandlerAdapter {

        /*
        1、读取数据事件   可以读取客户端发送的消息
        2、ChannelHandlerContext tx ： 上下文对象，含有pipeline管道、通道、地址
        3、Object msg ： 客户端发送的消息
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //将msg转换成byteBuffer
            ByteBuf buf = (ByteBuf) msg;
            System.out.println("服务端接收到客户端" + ctx.channel().remoteAddress()+ "发来的消息：" + buf.toString(CharsetUtil.UTF_8));
            //super.channelRead(ctx, msg);

            //如果此处的处理时间过长，使用异步方式进行业务处理

            //1、自定义普通任务  提交到taskQueue  添加多个普通任务时耗时累加；
            ctx.channel().eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    //处理业务逻辑
                }
            });

            //2、自定义定时任务  提交到scheduleTaskQueue
            ctx.channel().eventLoop().schedule(new Runnable() {
                @Override
                public void run() {
                    //处理业务逻辑
                }
            },5L, TimeUnit.SECONDS);

            //3、非当前的Reactor线程调用channel的方法


        }

        /*
        数据读取完毕，返回数据给客户端
         */
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            // 将数据写入到缓冲并刷新,发送的数据需要编码
            ctx.writeAndFlush(Unpooled.copiedBuffer("服务端返回数据",CharsetUtil.UTF_8));
            //super.channelReadComplete(ctx);
        }

        /*
        处理异常(异常处理器)
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("和" + ctx.channel().remoteAddress() + "通信发生异常");
            ctx.close();
            //super.exceptionCaught(ctx, cause);
        }
    }

}
