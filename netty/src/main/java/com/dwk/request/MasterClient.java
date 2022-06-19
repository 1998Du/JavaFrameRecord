package com.dwk.request;

import io.netty.bootstrap.ServerBootstrap;

/**
 * 服务端，接受客户端请求-处理-响应(发送回执)
 */
public class MasterClient {

    private static int HEAD_LENGTH = 4;

    //监听端口
    public void bind(int port){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
    }

}
