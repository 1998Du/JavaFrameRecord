package com.dwk.rabbitmq.model_workqueue;

import com.dwk.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;

/**
 * 生产者
 */
public class ProductDemo {

    public static int scan(){
        return new Scanner(System.in).nextInt();
    }

    public static void test(){
        Channel channel = RabbitMqUtil.getChannel();
        String msg = "工作队列";
        try {
            //生成队列
            channel.queueDeclare("NEWBEE",false,false,true,null);
            //发消息
            channel.basicPublish("","NEWBEE",null,msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int scan = scan();
        while (scan-- != 0){
            test();
            System.out.println("消息发送成功");
        }
    }

}
