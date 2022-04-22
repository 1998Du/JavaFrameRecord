package com.dwk.list;

import java.util.ArrayList;

/**
 * ArrayList
 * 数据结构：数组
 * 1、初始容量10
 * 2、扩容 1.5倍
 * 3、扩容调用Arrays.copyOf(老数组，新容量)
 */
public class ArrayListDemo {

    public static void main(String[] args) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("newbee");
    }

}
