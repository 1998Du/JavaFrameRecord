package com.dwk.netty.demo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class NettyClientDemo {

    public static void main(String[] args) {
        new NettyClientDemo().nettyClient();
    }

    /**
     * 客户端
     */
    public void nettyClient(){
        // 客户端需要一个事件循环组
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            //创建启动配置
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors) //设置线程组
                    .channel(NioSocketChannel.class) //设置客户端通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        //设置客户端处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端初始化完成");
            //启动客户端连接服务端 并设置同步
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6666).sync();
            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            eventExecutors.shutdownGracefully();
        }
    }

    /**
     * 自定义客户端处理器
     */
    class NettyClientHandler extends ChannelInboundHandlerAdapter {

        //当通道就绪就会触发该方法
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //给服务器发送消息
            ctx.writeAndFlush(Unpooled.copiedBuffer("我是客户端", CharsetUtil.UTF_8));
            super.channelActive(ctx);
        }

        //当通道有读取事件时会触发该方法
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            System.out.println("客户端接受到服务端的消息:" + buf.toString(CharsetUtil.UTF_8));
            //super.channelRead(ctx, msg);
        }

        //异常处理器
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            super.exceptionCaught(ctx, cause);
        }
    }

}
