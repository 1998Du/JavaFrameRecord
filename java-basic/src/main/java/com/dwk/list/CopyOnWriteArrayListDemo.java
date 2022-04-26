package com.dwk.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 并发包中唯一的并发List
 * 对其进行的修改操作都是基于一个复制的数组（快照）上进行的，使用写时复制策略 不是操作的原list
 * 复制的新数组长度 = 老数组长度 + 1   所以CopyOnWriteArrayList为无界list
 *
 * CopyOnWriteArrayList  线程安全
 * 方法内添加ReentrantLock锁(独占锁)  先获取锁才可以进行操作
 * 1、无参创建时内部初始化一个长度为0的Object[]数组  该数组被volatile修饰  保证内存可见性
 * 2、每次添加都创建一个新数组  将老数组复制到新数组  新数组的长度比老数组大1
 *
 *
 * CopyOnWriteArrayList使用写时复制的策略来保证 list的一致性，而获取一修改一写入
 * 三步操作并不是原子性的，所以在增删改的过程中都使用了独占锁，来保证在某个时间
 * 只有一个线程能对 list 数组进行修改 另外CopyOnWriteArrayList提供了弱一致性的迭代器
 * 从而保证在获取迭代器后其他线程对lisr的修改是不可见的，迭代器遍历的数组是一个快照
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        //添加元素的方法   原理类似
        copyOnWriteArrayList.add("");
        copyOnWriteArrayList.add(1,"");
        copyOnWriteArrayList.addIfAbsent("");
        copyOnWriteArrayList.addAllAbsent(new ArrayList());

        System.out.println("*********测试迭代器的弱一致性*********");
        test02();

        System.out.println("*********测试写时复制的弱一致性*********");
        int i = 0;
        while (true){
            System.out.println("==============第"+i+"次操作==============");
            test01();
            i++;
        }

    }

    /**测试写时复制的弱一致性问题,不能确保任何时刻保证数据的一致性*/
    static void test01() {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        Thread thread1 = new Thread(() -> {
            //线程1执行查询操作,get操作分两步，1、获取CopyOnWriteArrayList内部的array 2、根据下标获取元素
            //get方法没有加锁  因此get执行过程中其他线程可以操作CopyOnWriteArrayList
            String value = list.get(0).toString();
            if (!("1").equals(value)){
                System.out.println("================================【"+value+"】====================================");
            }
        });

        Thread thread2 = new Thread(() -> {
            //线程2执行删除操作
            list.remove(0);
        });

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**测试弱一致性的迭代器*/
    static void test02() {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        Thread thread = new Thread(() -> {
            list.add(0,"newbee");
            list.remove(1);
            list.add("666");
        });
        //返回迭代器后，其他线程对list的增删改对迭代器都是不可见的
        Iterator<Object> iterator = list.iterator();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

}
