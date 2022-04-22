package com.dwk.thread;
/**
 * wait  阻塞线程
 */
public class WaitDemo {

    private Object object = new Object();

    public void threads() throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            synchronized (object){
                try {
                    System.out.println("线程1   获取对象锁");
                    System.out.println("线程1   阻塞，释放锁");
                    object.wait();
                    System.out.println("线程1   被唤醒，执行完毕");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized(object){
                try {
                    System.out.println("线程2   获取对象锁");
                    System.out.println("线程2   阻塞，释放锁");
                    object.wait();
                    System.out.println("线程2   被唤醒，执行完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        Thread thread3 = new Thread(() -> {
            synchronized(object){
                System.out.println("线程3   获取对象锁");
                System.out.println("线程3   调用notify");
                object.notify();
                System.out.println("线程3   执行完毕，释放锁");
            }

        });

        thread1.start();
        thread1.join();
        thread2.start();
        Thread.sleep(1000);


    }

    public static void main(String[] args) throws InterruptedException {
        WaitDemo waitTest = new WaitDemo();
        waitTest.threads();
    }

}
