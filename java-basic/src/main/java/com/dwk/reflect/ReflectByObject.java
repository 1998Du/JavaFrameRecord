package com.dwk.reflect;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;

/**
 * 通过对象反射
 */
public class ReflectByObject {

    public static void main(String[] args) {
        Worker worker = (Worker) byObject(new Worker());
        System.out.println("通过对象反射：obj.getClass()" + JSONObject.toJSONString(worker));
    }

    /**利用反射组装一个对象*/
    static Object byObject(Worker worker){
        Object returnObj = null;
        try {
            Class<? extends Worker> workerClass = worker.getClass();
            //通过反射实例化一个对象
            Worker workerInstance = workerClass.newInstance();
            //获取Worker中的所有属性
            Field[] fields = workerClass.getDeclaredFields();
            for (Field field : fields) {
                //设置属性可访问
                field.setAccessible(true);
                //获取属性的类型
                Class<?> type = field.getType();
                //给对应的属性赋值
                if (Integer.class.equals(type)){
                    field.set(workerInstance,20);
                }else if(String.class.equals(type)){
                    //获取属性名
                    String name = field.getName();
                    if (("name").equals(name)){
                        field.set(workerInstance,"newbee");
                    }else if (("job").equals(name)){
                        field.set(workerInstance,"coder");
                    }
                }
            }
            returnObj = workerInstance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return returnObj;
    }

}
