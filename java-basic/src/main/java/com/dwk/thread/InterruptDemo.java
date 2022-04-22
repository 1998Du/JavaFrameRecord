package com.dwk.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 中断线程
 */
public class InterruptDemo {

    //获取当前时间，精确到秒
    public static void nowTime(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String now = format.format(date);
        System.out.println("当前时间："+now);
    }

    /**
     * 测试调用interrupt()是否立即中断
     */
    public void test1(){
        Thread thread1 = new Thread(() -> {
            //当前线程没有被中断的话就一直执行
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("线程1   执行");
            }
            System.out.println("线程1   中断状态：" + Thread.currentThread().isInterrupted());
            System.out.println("线程1   执行完毕");
        });

        Thread thread2 = new Thread(() -> {
            try {
                System.out.println("线程2   执行");
                System.out.println("线程2   中断线程1");
                //中断线程1
                thread1.interrupt();
                //为了查看调用interrupt()后的逻辑  让线程2休眠五秒
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程2   执行完毕");
        });

        thread1.start();
        thread2.start();
    }

    /**
     * 测试A线程阻塞期间，B线程调用A的interrupt()方法
     */
    public void test2() {

        Thread thread1 = new Thread(() -> {
            try {
                //当前线程没有被中断的话就一直执行
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("线程1   执行");
                    nowTime();
                    //让线程1休眠5秒
                    System.out.println("线程1   休眠5秒");
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nowTime();
            System.out.println("线程1   中断状态："+Thread.currentThread().isInterrupted());
            System.out.println("线程1   执行完毕");
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("线程2   执行");
                System.out.println("线程2   中断线程1");
                //中断线程1
                thread1.interrupt();
                //为了查看调用interrupt()后的逻辑  让线程2休眠五秒
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程2   执行完毕");
        });

        thread1.start();
        thread2.start();
    }


    public static void main(String[] args) {
        InterruptDemo interruptTest = new InterruptDemo();
        interruptTest.test2();
    }

}
