package com.dwk.netty.rpc.app;

import com.dwk.netty.rpc.client.ClientService;
import com.dwk.netty.rpc.common.CommonInfo;

/**
 * 服务消费者启动器
 */
public class ClientApp {

    /**协议头*/
    private static final String PROTOCOL = "/";

    private static ClientService clientService;

    public String RPC(String msg){
        ClientService clientService = new ClientService();
        CommonInfo commonInfo = (CommonInfo) clientService.getBean(CommonInfo.class, PROTOCOL);
        return commonInfo.connect(msg);
    }

}
