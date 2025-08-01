package com.dwk.atomic;

import sun.misc.Unsafe;

/**
 * Unsafe demo,Unsafe类不属于Java标准，若要使用可以使用反射将Unsafe的theUnsafe属性设置成true
 */
public class UnsafeDemo {

    //获取Unsafe实例
    private static Unsafe unsafe = Unsafe.getUnsafe();

    //记录变量在类中的偏移值
    private static long offset;

    //变量
    private volatile long state = 0L;

    static {
        try {
            //获取变量state在类UnsafeTest中的偏移量
            offset = unsafe.objectFieldOffset(UnsafeDemo.class.getField("state"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //创建实例并设置变量state的值为1
        UnsafeDemo unsafeTest = new UnsafeDemo();
        /*
        compareAndSwap参数含义
        1、Object o ：对象内存位置
        2、Long l ： 对象中的变量的偏移量
        3、Long l1 ：变量预期值
        4、Long l2 ：新的值
        如果对象o中内存偏移量为l的便两个值为l1，则使用新的值l2替换旧的值l2
        这是处理器提供的一个原子性的指令
         */
        boolean b = unsafe.compareAndSwapLong(unsafeTest, offset, unsafeTest.state, 1);
        System.out.println("原子性写操作"+b);
        System.out.println("原子性操作后变量state的值："+unsafeTest.state);
    }

}
