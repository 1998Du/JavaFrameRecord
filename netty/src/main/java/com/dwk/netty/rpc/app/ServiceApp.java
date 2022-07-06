package com.dwk.netty.rpc.app;

import com.dwk.netty.rpc.service.RemoteService;

/**
 * 服务提供方启动器
 */
public class ServiceApp {

    public static void main(String[] args) {
        RemoteService.start("127.0.0.1",6666);
    }

}
