package com.dwk.netty.rpc.test;

import com.dwk.netty.rpc.app.ClientApp;

/**
 * 测试RPC
 */
public class Test {

    public static void main(String[] args) {
        ClientApp clientServiceApp = new ClientApp();
        String result = clientServiceApp.RPC("test");
        System.out.println(result);
    }

}
