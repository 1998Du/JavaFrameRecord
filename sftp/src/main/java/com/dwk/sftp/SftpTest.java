package com.dwk.sftp;

import java.util.Scanner;

public class SftpTest {

    private static final String HOST = "请输入ip:";
    private static final String PORT = "请输入端口:";
    private static final String USERNAME = "请输入用户名:";
    private static final String PASSWORD = "请输入密码:";
    private static final String LOADING = "正在连接服务器...";
    private static final String SHUTDOWN = "=======输入shutdown关闭连接=====";

    public String scan(){
        System.out.format("\33[36;4m> ");
        return new Scanner(System.in).next();
    }

    public void print(String tip){
        System.out.format("\33[33;4m" + tip + "%n");
    }

    public void test(){
        print(HOST);
        String host = scan();
        print(PORT);
        int port = Integer.valueOf(scan());
        print(USERNAME);
        String username = scan();
        print(PASSWORD);
        String password = scan();
        print(LOADING);
        SftpUtil.setAttr(host,port,username,password);
        SftpUtil.login();
        print(SHUTDOWN);
        while (true) {
            String command = scan();
            if (("shutdown").equals(command)) {
                SftpUtil.shutdown();
            } else {
                //执行shell命令
                String result = SftpUtil.runShell(command);
                System.out.format("\33[31;4m" + result + "%n");
            }
        }
    }

    public static void main(String[] args) {
        SftpTest sftpTest = new SftpTest();
        sftpTest.test();
    }

}
