package com.dwk.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * BIO案例实现
 */
public class BioDemo {

    public static void main(String[] args) {

    }

}

/**服务器端*/
class ServerClient{

    public static void main(String[] args) {
        //可以使用cmd的telnet作为客户端连接
        connect();
    }

    /**连接*/
    public static void connect(){
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        try {
            //监听端口
            ServerSocket serverSocket = new ServerSocket(6666);
            System.out.println("==============服务端启动");
            //等待连接
            while (true){
                //等待客户端连接时，线程会阻塞！
                Socket accept = serverSocket.accept();
                System.out.println("===========客户端连接");
                cachedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        handler(accept);
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**和客户端通讯*/
    public static void handler(Socket socket){
        try {
            System.out.println("[" + Thread.currentThread().getId() + "," + Thread.currentThread().getName() + "]");
            byte[] bytes = new byte[1024];
            //通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
            //循环读取客户端发送的数据
            while (true){
                System.out.println("[" + Thread.currentThread().getId() + "," + Thread.currentThread().getName() + "]");
                //阻塞读取客户端发送的数据，客户端连接成功但是没有发送数据的时候 此处线程阻塞！
                int read = inputStream.read(bytes);
                if (read != -1){
                    //打印客户端发送的数据
                    System.out.println(new String(bytes,"UTF-8"));
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println("=========关闭socket连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

