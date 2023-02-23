package com.dwk.lock;

import java.util.concurrent.TimeUnit;

/**
 * 实现线程同步
 */
public class SynchronizedDemo {

    //作用在不同位置锁住的是什么元素


    // 作用在静态方法上，锁住的是类
    public static synchronized void method1() {
        System.out.println("作用在静态方法上");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("作用在静态方法上结束");
    }

    // 作用在普通方法上,锁住的是方法的调用者
    public synchronized void method2() {
        System.out.println("作用在普通方法上");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("作用在普通方法上结束");
    }

    // 作用在代码块是上，锁住的是传入的对象   synchronized (对象)：锁对象     synchronized (类)：锁类
    public void method3() {
        synchronized (this){
            System.out.println("作用在代码块上");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("作用在代码块上结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
        Thread thread = new Thread(() -> {
            method1();
        });

        Thread thread1 = new Thread(() -> {
            synchronizedDemo.method2();
        });
        thread.start();
        thread1.start();
    }
}
