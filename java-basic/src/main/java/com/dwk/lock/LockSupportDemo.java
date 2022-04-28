package com.dwk.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport工具类相关知识点
 *
 * 1、主要作用：挂起和唤醒线程，是创建锁和其他同步类的基础
 * 2、与每个使用它的线程都会关联一个许可证，默认情况下调用LockSupport类的方法的线程不允许持有许可证
 */
public class LockSupportDemo {

    public static void main(String[] args) throws InterruptedException {
        testParkAndUnpark();
        testParkNanos();
    }

    /**测试park和unpark方法 - 阻塞线程和唤醒线程*/
    static void testParkAndUnpark(){
        Thread thread1 = new Thread(() -> {
            System.out.println("调用线程默认不允许持有与LockSupport关联的许可证，调用时被阻塞！");
            //如果调用park方法的线程已经拿到了与LockSupport关联的许可证，则调用pack时会立刻返回，否则调用会被禁止参与线程调度，即被阻塞
            LockSupport.park();
            System.out.println("调用park方法线程被唤醒");
        });
//        thread1.start();

        Thread thread2 = new Thread(() -> {
            //使用unpark()唤醒线程1
            System.out.println("线程2调用unpark唤醒调用park而阻塞的线程");
            LockSupport.unpark(thread1);
        });
//        thread2.start();

        Thread thread3 = new Thread(() -> {
            //使用interrput()方法唤醒线程
            System.out.println("线程3调用interrupt()唤醒阻塞线程");
            thread1.interrupt();
        });
//        thread3.start();

        Thread thread4 = new Thread(() -> {
            System.out.println("线程先调用unpark获取与LockSupport关联的许可证，再调用park会立刻返回");
            LockSupport.unpark(Thread.currentThread());
            System.out.println("调用了unpark，开始调用park");
            LockSupport.park();
            System.out.println("执行结束");
        });
//        thread4.start();

        Thread thread5 = new Thread(() -> {
            System.out.println("子线程调用park阻塞自己，主线程调用unpark唤醒子线程");
            LockSupport.park();
            System.out.println("子线程执行结束!");
        });
//        thread5.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程开始调用unpark");
        LockSupport.unpark(thread5);
    }

    /**测试parkNanos方法 - 阻塞线程和自动唤醒*/
    static void testParkNanos(){
        Thread thread1 = new Thread(() -> {
            System.out.println("子线程调用parkNanos阻塞自己，设置五秒后自动唤醒");
            LockSupport.parkNanos(5000);
            System.out.println("线程执行结束!");
        });
        thread1.start();


    }

}
