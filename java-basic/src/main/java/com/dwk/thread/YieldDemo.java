package com.dwk.thread;

/**
 * 让出线程
 */
public class YieldDemo {

    public void test1(){
        Thread thread1 = new Thread(()->{
            System.out.println("线程1   执行当中");
            System.out.println("线程1   暗示线程，可以进行新一轮的线程调度");
            Thread.yield();
            System.out.println("线程1   放弃cpu执行权");
        });
        thread1.start();
    }

    public void test2(){
        Thread thread1 = new Thread(()->{
            System.out.println("线程2   执行当中");
            System.out.println("线程2   暗示线程，可以进行新一轮的线程调度");
            Thread.yield();
            System.out.println("线程2   放弃cpu执行权");
        });
        thread1.start();
    }

    public static void main(String[] args) {

        YieldDemo yieldTest = new YieldDemo();
        yieldTest.test1();
        yieldTest.test2();

    }

}
