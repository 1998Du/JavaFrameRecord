package com.dwk.rabbitmq.model_easy;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProductDemo {

//    队列名称
    private static final String QUEUE_NAME = "NEWBEE";

    public static void test(){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            factory.setUsername("newbee");
            factory.setPassword("newbee");
            //连接
            Connection connection = factory.newConnection();
            //获取消息通道
            Channel channel = connection.createChannel();
            /**
             * 生成队列
             * 1、队列名称
             * 2、队列里的消息是否持久化
             * 3、该队列是否只供一个消费者消费即是否消息共享
             * 4、是否自动删除
             * 5、其他参数 （延迟。。）
             */
            channel.queueDeclare(QUEUE_NAME,false,false,true,null);
            String msg = "hello word";
            /**
             * 发消息
             * 1、交换机
             * 2、路由的key值
             * 3、其他参数
             * 4、需要发送的消息
             */
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test();
        System.out.println("消息发送成功");
    }

}
