package com.dwk.rabbitmq.model_easy;

import com.rabbitmq.client.*;

/**
 * 消费者
 */
public class ConsumerDemo {

    private static final String QUEUE_NAME = "NEWBEE";

    public static void test(){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            factory.setUsername("newbee");
            factory.setPassword("newbee");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            /**
             * 消费消息
             * 1、消费哪个队列
             * 2、消费成功之后是否要自动应答
             * 3、消费者成功消费的回调  即消费消息
             * 4、消费者取消消费
             */
            //声明接收消息
            DeliverCallback deliverCallback = (consumerTag,message)->{
                System.out.println(new String(message.getBody()));
            };
            //取消消息时的回调
            CancelCallback cancelCallback = consumerTag->{
                System.out.println("消息消费中断");
            };
            channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test();
    }

}
