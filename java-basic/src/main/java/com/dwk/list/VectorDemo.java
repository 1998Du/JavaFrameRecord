package com.dwk.list;

import java.util.Vector;

/**
 * 线程安全list vector   性能不好！内部大部分方法都使用synchronized
 *
 * 1、无参创建时指定容量 10
 * 2、有参(Collection)创建 参数转数组，有参(int,int)创建  指定初始容量和扩容容量
 * 3、调用add(object)方法  add方法受synchronized保护
 * 4、调用add(index,object)
 * 5、扩容：若未指定扩容容量则容量增长一倍
 * 6、最大容量：Integer.MAX_VALUE - 8
 * 7、扩容后将老数组赋值到新数组  Arrays.copyOf(elementData, newCapacity);
 */
public class VectorDemo {

    public static void main(String[] args) {
        Vector<Object> vector = new Vector<>();
        vector.add("");
        vector.add(0,"");
    }

}
