package com.dwk.thread;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * 线程创建的几种方式
 */
public class CreateThreadDemo {

    //    volatile保证内存可见性
    private static volatile Object A = new Object();
    private static volatile Object B = new Object();

    //    1、继承Thread类
    public static class ThreadTest extends Thread {

        public void test(){
            run();
        }

        @Override
        public void run() {
            try {
                System.out.println("继承Thread，创建一个线程");
                synchronized (A) {
                    System.out.println(this.getName() + "获取对象A的锁,不释放\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //    2、实现Runnable接口
    public static class RunnableTest implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("实现Runnable接口，创建一个线程");
                synchronized (A) {
                    System.out.println("Runnable    获取对象A的锁\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //    3、实现Callable接口
    public static class CallableTest implements Callable {

        @Override
        public String call() throws Exception {
            System.out.println("实现Callable接口，创建一个线程");
            return "实现Callable接口，线程执行返回结果\n";
        }
    }

    //    4、线程池创建
    public static class ThreadPoolTest {
        //使用Executors创建线程池有OOM风险
        //jdk中四种线程池 1、FixedThreadPool  2、SingleThreadPool  3、CacheThreadPool  4、ScheduledThreadPool
        //四种线程池允许创建的线程数量都是Integer.MAX_VALUE,大量堆积可能造成OOM 耗尽资源
        static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10    //核心线程数
                , 10     //线程池内最大线程存活数
                , 0        //线程存活时间,线程池内线程数量大于核心线程数时其他空余线程的最大存活时间
                , TimeUnit.SECONDS    //时间单位
                , new LinkedBlockingQueue<>(5) //阻塞队列，LinkedBlockingQueue-无界,当新任务来的时候会先判断当前运行的线程数量是否达到核心线程数，如果达到的话，新任务就会被存放在队列中,按顺序占用线程执行
                , Executors.defaultThreadFactory() //线程工厂
                , new ThreadPoolExecutor.AbortPolicy()); //拒绝策略
        public static void createThread(){
            threadPoolExecutor.execute(()->{
                System.out.println("线程池创建线程\n");
            });
        }
    }

    //    5、TimerThread定时任务创建
    public static class TimerThreadTest{
        static Timer timer = new Timer();

        public static void test(){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("定时任务");
                }
            }, 2000);
        }
    }

    //    6、new Thread
    public static class NewThread{
        public static void test(){
            new Thread(()->{
                System.out.println("暴力创建！\n");
            }).start();
        }
    }

    /*    7、创建线程池的几种方式
        1、通过Executors工厂方法创建（
                    newFixedThreadPool、  创建一个固定大小的线程池
                    newCachedThreadPool、 带缓存的线程池，适用短时间内有大量任务的场景但有可能会占用更多的资源，线程数量随任务量而定
                    newSingleThreadExecutors、  创建单个线程的线程池
                    newSingleThreadScheduledExecutor、  创建执行定时任务的单个线程的线程池
                    newScheduledThreadPool、  创建执行定时任务的线程池
                    newWorkStealingPool  根据当前设备的配置自动生成线程池）
        2、ThreadPoolExecutors  手动创建
     */



    public static void main(String[] args) {

        int i = -1;

        //创建线程
        ThreadTest threadTest = new ThreadTest();
        Thread thread1 = new Thread(threadTest);
        thread1.start();

        RunnableTest runnableTest = new RunnableTest();
        Thread thread2 = new Thread(runnableTest);
        thread2.start();

        CallableTest callableTest = new CallableTest();
        FutureTask futureTask = new FutureTask<>(callableTest);
        Thread thread3 = new Thread(futureTask);
        thread3.start();

        ThreadPoolTest.createThread();

        TimerThreadTest.test();

        NewThread.test();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //实现Callable接口方式，，获取线程执行结果
        try {
            String result = (String) futureTask.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
