package com.dwk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {

    public static void main(String[] args) {
        finallyTest();
    }

    /**
     * 不管try和catch里怎么执行   finally最后一定会被执行
     */
    static void finallyTest(){
        try{
            System.out.println("执行try");
            throw new RuntimeException("异常");
        }catch (Exception e){
            System.out.println("catch执行"+e.getMessage());
            return;
        }finally {
            System.out.println("finally 执行了");
        }
    }

    /**
     * String  final修饰  对象不可变 每次对String对象操作相当于操作一个新对象
     * StringBuffer  可变  线程安全   效率慢  所有的方法都用synchronized修饰
     * StringBuilder 可变  线程不安全  效率高
     */
    static void stringStringBufferStringBuilderTest(){
        String s = "";
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
    }

    /**
     * this 本质 指向本对象的指针
     * super  本质  关键字  super()必须写在子类构造方法第一行
     * 二者都指向对象
     */

    /**
     * 定义泛型方法
     */
    public <T> T getObjectByClassName(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        T t = clazz.newInstance();
        return t;
    }

    /**
     * try中打开资源  try执行完成或者catch后try中的资源会自动关闭
     */
    static void tryWithResource(){
        try(FileInputStream inputStream = new FileInputStream(new File(""));){

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
