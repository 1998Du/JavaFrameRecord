package com.dwk.thread;

/**
 * 线程休眠，阻塞线程，不释放资源锁
 */
public class SleepDemo {

    private Object object = new Object();

    public SleepDemo() {

        Thread thread1 = new Thread(() -> {
            synchronized (object){
                try {
                    System.out.println("线程1   获取到对象锁");
                    System.out.println("线程1   开始休眠");
                    Thread.sleep(3000);
                    System.out.println("线程1   休眠结束");
                    System.out.println("线程1   执行结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (object){
                try {
                    System.out.println("线程2   获取到对象锁");
                    System.out.println("线程2   开始休眠");
                    Thread.sleep(3000);
                    System.out.println("线程2   休眠结束");
                    System.out.println("线程2   执行结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        thread1.start();
        thread2.start();
    }

    public static void main(String[] args) {
        new SleepDemo();
    }

}
