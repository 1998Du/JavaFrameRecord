package com.dwk.netty.rpc.provider;

import com.dwk.netty.rpc.common.CommonInfo;

/**
 * 客户端调用
 */
public class ProviderService implements CommonInfo {

    // 当有消费方调用时返回一个结果
    @Override
    public String connect(String msg) {
        System.out.println("provider执行了");
        String returnStr = null;
        if (msg != null){
            returnStr = "客户端调用Provider，参数为：" + msg;
        }else{
            returnStr = "客户端发送的消息为null";
        }
        return returnStr;
    }
}
