package com.dwk.datautil.util;

import com.alibaba.fastjson.JSONObject;
import com.dwk.datautil.chain.*;

import java.lang.reflect.Field;

/**
 * 
 * Description:
 *      反射工具类
 * @author: mushi
 * @Date: 2021/4/8 15:20
 */
public class ReflectUtil {

    /**
     * 初始化类的对象
     * @param metaClass 反射类
     * @param metaData 元数据
     * @return
     */
    public Object initObject(Class<?> metaClass, String metaData){

        Object newInstance = null;
        Field[] declaredFields = metaClass.getDeclaredFields();

        try {
            newInstance = metaClass.newInstance();

            //创建责任链String->boolean->returnType->list->map->对象属性
            ChainHandler chainBegin = new ConcreteString();
            ChainHandler booleanConcrete = new ConcreteBoolean();
            ConcreteList listConcrete = new ConcreteList();
            ConcreteMap mapConcrete = new ConcreteMap();
            ChainHandler objectConcrete = new ConcreteObject();

            chainBegin.setNext(booleanConcrete);
            booleanConcrete.setNext(listConcrete);
            listConcrete.setNext(mapConcrete);
            mapConcrete.setNext(objectConcrete);


            JSONObject docJson = JSONObject.parseObject(metaData);
            for (Field field : declaredFields){
                field.setAccessible(true);
                String key = field.getName();
                String fieldGenericType = field.getGenericType().toString();

                //System.out.println(key+"属性类型："+fieldGenericType);
                //责任链
                chainBegin.doInit(fieldGenericType,metaClass,newInstance,docJson,key);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return newInstance;
    }

}
