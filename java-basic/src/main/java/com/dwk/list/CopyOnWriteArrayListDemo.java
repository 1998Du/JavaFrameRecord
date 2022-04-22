package com.dwk.list;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList  线程安全
 * 方法内添加ReentrantLock锁  先获取锁才可以进行操作
 * 1、无参创建时内部初始化一个长度为0的Object[]数组  该数组被volatile修饰  保证内存可见性
 * 2、每次添加都创建一个新数组  将老数组复制到新数组  新数组的长度比老数组大1
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        copyOnWriteArrayList.add("");
    }

}
