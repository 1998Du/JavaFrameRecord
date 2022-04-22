package com.dwk.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * cas操作
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
    }

}
