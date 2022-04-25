package com.dwk.reflect;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 通过调用类进行反射,组织一个 {name = newbee,age = 20, job = coder} 的Worker对象
 */
public class ReflectByClass {

    public static void main(String[] args) {
        Worker worker1 = byClassOne();
        System.out.println("通过类名反射 xxx.class：" + JSONObject.toJSONString(worker1));
        Worker worker2 = byClassTwo();
        System.out.println("通过路径反射 Class.forName()：" + JSONObject.toJSONString(worker2));
        Worker worker3 = byClassThree();
        System.out.println("通过set方法赋值 Method.invoke()：" + JSONObject.toJSONString(worker3));
    }

    /**通过类名反射*/
    static Worker byClassOne(){
        Worker returnWorker = null;
        try {
            Class<Worker> workerClass = Worker.class;
            //实例化一个对象
            returnWorker = workerClass.newInstance();
            //给对象赋值
            returnWorker.setName("newbee");
            returnWorker.setAge(20);
            returnWorker.setJob("coder");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return returnWorker;
    }

    /**通过路径反射*/
    static Worker byClassTwo(){
        Worker returnWorker = null;
        try {
            String className = "com.dwk.reflect.Worker";
            Class<?> workerClass = Class.forName(className);
            //创建一个实例
            Object instance = workerClass.newInstance();
            //获取类中所有属性
            Field[] declaredFields = workerClass.getDeclaredFields();
            for(Field field : declaredFields){
                //设置属性可见
                field.setAccessible(true);
                //获取属性类型
                Class<?> type = field.getType();
                if (Integer.class.equals(type)){
                    field.set(instance,20);
                }else if(String.class.equals(type)){
                    //获取属性名
                    String name = field.getName();
                    if (("name").equals(name)){
                        field.set(instance,"newbee");
                    }else if (("job").equals(name)){
                        field.set(instance,"coder");
                    }
                }
            }
            returnWorker = (Worker) instance;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return returnWorker;
    }

    /**以路径反射为例，根据属性组装set方法赋值*/
    static Worker byClassThree(){
        Worker returnWorker = null;
        try {
            String className = "com.dwk.reflect.Worker";
            Class<?> workClass = Class.forName(className);
            //创建一个实例
            Object instance = workClass.newInstance();
            //获取类中所有实例
            Field[] declaredFields = workClass.getDeclaredFields();
            for (Field field : declaredFields){
                //获取属性名
                String fieldName = field.getName();
                //获取属性类型
                Class<?> type = field.getType();
                //组装set方法
                String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                if (Integer.class.equals(type)){
                    //getMethod()第一个参数为方法名，后面按顺序填写参数类型
                    Method method = workClass.getMethod(methodName, Integer.class);
                    method.invoke(instance,20);
                }else if(String.class.equals(type)){
                    Method method = workClass.getMethod(methodName, String.class);
                    if (("name").equals(fieldName)){
                        method.invoke(instance,"newbee");
                    }else if(("job").equals(fieldName)){
                        method.invoke(instance,"coder");
                    }
                }
            }
            returnWorker = (Worker) instance;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return returnWorker;
    }

}
