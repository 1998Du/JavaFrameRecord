package com.dwk.netty.demo2.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.util.WeakHashMap;

public class NettyHttpServiceImp {

    public static void main(String[] args) {
        new NettyHttpServiceImp().server();
    }

    public void server(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            //服务端启动配置
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());
            //绑定端口启动服务
            ChannelFuture channelFuture = serverBootstrap.bind(6666).sync();

            //添加bind监听器
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()){
                        System.out.println("服务端启动成功");
                    }else{
                        System.out.println("服务端启动失败");
                    }
                }
            });

            //监听关闭端口
            channelFuture.channel().closeFuture().sync();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}

/**
 * 服务端初始化器  处理pipeline
 */
class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //获取管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        //添加解码器   HttpServerCodec-netty提供的Http编解码器
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        //添加自定义处理器
        pipeline.addLast("MyServerHandler",new ServerHandler());
    }
}

/**
 * 服务端自定义处理器
 * SimpleChannelInboundHandler extends ChannelInboundHandlerAdapter
 * HttpObject : 客户端和服务端相互通讯的数据被封装成 HttpObject
 */
class ServerHandler extends SimpleChannelInboundHandler<HttpObject>{

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {
        //判断消息类型
        if (msg instanceof HttpRequest){
            System.out.println("开始处理Http请求...");

            // 过滤请求(根据uri过滤)
            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri)){
                return;
            }

            // 回复信息给浏览器 回复的消息要符合Http协议
            ByteBuf content = Unpooled.copiedBuffer("我是服务器返回的消息", CharsetUtil.UTF_8);
            //构建http响应
            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"application/json");
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            //将构建好的response返回
            channelHandlerContext.writeAndFlush(defaultFullHttpResponse);

        }
    }
}
