package com.dwk.rabbitmq.model_workqueue;

import com.dwk.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConsumerDemo implements Consumer,Runnable {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,10,1, TimeUnit.MINUTES,new LinkedBlockingDeque<>());

    Channel channel = RabbitMqUtil.getChannel();

    public int scan(){
        return new Scanner(System.in).nextInt();
    }

    public void test(){
        //消费消息
        try {
            channel.basicConsume("NEWBEE",new ConsumerDemo());
        } catch (IOException e) {
            int scan = scan();
            if (scan == 1){
                test();
            }
        }
    }

    public static void main(String[] args) {
        ConsumerDemo consumerDemo = new ConsumerDemo();
        threadPoolExecutor.execute(consumerDemo);
    }

    @Override
    public void handleConsumeOk(String s) {

    }

    @Override
    public void handleCancelOk(String s) {

    }

    @Override
    public void handleCancel(String s) throws IOException {

    }

    @Override
    public void handleShutdownSignal(String s, ShutdownSignalException e) {

    }

    @Override
    public void handleRecoverOk(String s) {

    }

    @Override
    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
        System.out.println(Thread.currentThread().getName()+new String(bytes));
        //应答  表示已经接收到消息 mq可以将消息从队列中删除      布尔值表示批量处理消息
        channel.basicAck(envelope.getDeliveryTag(),false);
    }

    @Override
    public void run() {
        test();
    }
}
