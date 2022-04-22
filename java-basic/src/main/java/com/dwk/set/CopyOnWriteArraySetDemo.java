package com.dwk.set;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 线程安全的set
 */
public class CopyOnWriteArraySetDemo {

    public static void main(String[] args) {

    }

    public static void test(){
        CopyOnWriteArraySet<Object> set = new CopyOnWriteArraySet<>();
    }

}
