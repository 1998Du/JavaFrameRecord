package com.dwk.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 原子操作类
 */
public class AtomicDemo {

    private static AtomicLong atomicLong = new AtomicLong();

    private static int num = 0;

    // 两个数组中一共有7个0  使用atomicLong进行统计
    private static int[] one = {0,0,0,1,1,1,2,2,2};
    private static int[] two = {12,11,10,0,2,0,0,1,0};

    public void countZeroWithAtomic() throws InterruptedException {
        // 分别用两个线程统计两个数组中0的个数，统计量都用atomiclong存储
        Thread thread1 = new Thread(()->{
            for (int i = 0;i<one.length;i++){
                if (one[i] == 0){
                    atomicLong.incrementAndGet();
                }
            }
        });

        Thread thread2 = new Thread(()->{
            for (int i = 0;i<two.length;i++){
                if (two[i] == 0){
                    atomicLong.incrementAndGet();
                }
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("原子操作类统计两个数组中共有："+atomicLong.get()+"个0");
    }

    public void countZero() throws InterruptedException {
        // 分别用两个线程统计两个数组中0的个数，统计量都用atomiclong存储
        Thread thread1 = new Thread(()->{
            for (int i = 0;i<one.length;i++){
                if (one[i] == 0){
                    num++;
                }
            }
        });

        Thread thread2 = new Thread(()->{
            for (int i = 0;i<two.length;i++){
                if (two[i] == 0){
                    num++;
                }
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("非原子操作类统计两个数组中共有："+num+"个0");
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicDemo atomicTest = new AtomicDemo();
        atomicTest.countZero();
        atomicTest.countZeroWithAtomic();
        //jdk8新增
        /**
        LongAdder longAdder = new LongAdder();
        LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return 0;
            }
        },0L);
         */
    }

}
