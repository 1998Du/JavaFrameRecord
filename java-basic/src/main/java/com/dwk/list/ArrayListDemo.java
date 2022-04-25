package com.dwk.list;

import java.util.ArrayList;

/**
 * ArrayList
 * 数据结构：数组
 * 1、ArrayList初始容量：10
 * 2、ArrayList扩容规则：老数组的1.5倍
 * 3、扩容方式是否为扩展老数组？  否！扩容方式为将老数组复制到一个新容量的数组；
 * 4、最大容量：Integer.MAX_VALUE；
 * 5、如果无法进行扩容且（老数组容量+1）< Integer.MAX_VALUE - 8 则直接将数组容量扩展为Integer.MAX_VALUE - 8；
 * 6、如果（老数组容量 + 1）> Integer.MAX_VALUE - 8 数组容量直接扩展为Integer.MAX_VALUE；
 * 7、为什么要使用MAX_ARRAY_SIZE作为允许的最大容量，而不是在无法扩容时直接将数组容量扩展至Integer.MAX_VALUE?
 *  不同的JVM对对象的存储结构不一样，有的JVM会保存一些对象头信息在数组里面导致数组最大容量为Integer.MAX_VALUE - 对象头所占用的容量，
 *  因此使用Integer.MAX_VALUE - 8 在中间做一层过渡，保证数组无法扩容但还不到最大容量时还可以继续添加新元素；
 * 8、add方法没有做任何同步操作，线程不安全；
 */
public class ArrayListDemo {

    public static void main(String[] args) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("newbee");
    }
}
