package com.dwk.set;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 线程安全的set集合
 * 通过Collections的synchronizedSet方法创建线程安全的集合；
 */
public class SynchronizedSetDemo {

    public static void main(String[] args) {

    }

    public static void test(){
        Set<Object> set = Collections.synchronizedSet(new LinkedHashSet<>());
        set.add("");
    }

}
