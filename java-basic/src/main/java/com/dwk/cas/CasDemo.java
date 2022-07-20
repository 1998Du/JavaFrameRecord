package com.dwk.cas;

import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * cas操作
 * cas操作存在ABA问题，即属性值可能被修改再被还原
 * 1、线程1--->获取到属性X-->X值为A
 * 2、线程1准备进行CAS操作
 * 3、线程2--->获取到属性X-->X值为A
 * 4、线程2--->CAS修改属性X--->X值为B
 * 5、线程2--->CAS修改属性X--->X值为A
 * 6、线程1--->执行CAS操作---->X值为B
 *
 * 线程1执行CAS操作期间因为线程2已经对X进行了修改和还原，所以线程1最终操作的A并不是第一步它获取到的A
 *
 * 产生原因：变量的状态值产生了环形转换
 * 解决办法：AtomicStampedReference类给每个变量的状态值都配备了一个时间戳从而避免ABA问题
 */
public class CasDemo {

    public static void main(String[] args) {
        int i = 10;
        CasDemo casOperationTest = new CasDemo();
        casOperationTest.creatThread(i);
    }

    public void creatThread(int i){
        while (i>=0){
            Thread thread = new Thread(() -> {
                test();
            });
            thread.start();
            i--;
        }
    }

    public void test(){
        AtomicStampedReference atomicStampedReference = new AtomicStampedReference(0,0);
        Unsafe unsafe = Unsafe.getUnsafe();
        unsafe.compareAndSwapInt(new Object(),1L,0,1);
    }

}
