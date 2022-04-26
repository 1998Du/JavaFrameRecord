package com.dwk.map;

import java.util.LinkedHashMap;

/**
 * LinkedHashMap 源码解析demo
 * 继承HashMap
 * 大部分操作基本和HashMap一样，数据结构也是数组+链表+红黑树
 * put过程中，LinkedHashMap重写了将元素放入节点的方法：newNode()，每当元素放入节点时LinkedHashMap会将这个节点追加到一条链表上
 * 如果put的元素已经存在，那么修改原节点的value，
 *  -如果LinkedHashMap中的属性accessOrder访问顺序设置为true且当前修改的节点不是最后一个则把这个节点移动到链表的最后：afterNodeAccess()
 */
public class LinkedHashMapDemo {

    public static void main(String[] args) {
        test();
    }

    static void test(){
        LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("key","value");
    }

}
