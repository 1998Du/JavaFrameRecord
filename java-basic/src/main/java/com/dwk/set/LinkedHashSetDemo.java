package com.dwk.set;

import java.util.LinkedHashSet;

/**
 * 底层：HashSet + LinkedHashMap
 * 存储还是用的HashMap  数据存储后执行一个插入后操作  afterNodeInsertion，维护链表
 * LinkedHashSet = HashSet + LinkedHashMap
 *
 * 线程不安全
 */
public class LinkedHashSetDemo {

    public static void main(String[] args) {

    }

    public static void test(){
        LinkedHashSet<Object> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("");
    }

}
