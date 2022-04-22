package com.dwk.list;

import java.util.LinkedList;

/**
 * LinkedList源码
 * 数据结构：双向链表
 * 1、无参创建时无任何操作
 * 2、有参(Collection类型)创建时将参全部添加到LinkedList
 * 3、调用add方法添加元素  尾插法将新节点放到最后因此有序  返回boolean值
 * 4、调用get时判断传入的index是否小于链表长度的1/2，如果是则从头节点开始往后遍历index次
 *      如果index大于链表长度的1/2，则从尾节点开始往前遍历index次
 *      保证遍历次数小于链表长度的1/2
 * 5、线程不安全
 * 6、优缺点，添加删除很快 获取慢最慢需要遍历1/2长度
 */
public class LinkedListDemo {

    public static void main(String[] args) {
        LinkedList<Object> linkedList = new LinkedList<>();
        linkedList.add("linkedlist");
        linkedList.remove(0);
        linkedList.get(0);
    }

}
