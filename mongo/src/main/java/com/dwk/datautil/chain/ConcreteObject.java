package com.dwk.datautil.chain;

import com.alibaba.fastjson.JSONObject;
import com.dwk.datautil.util.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *  初始化QueryDefineInfo对象
 * @author: mushi
 * @Date: 2021/4/8 10:15
 */
public class ConcreteObject extends ChainHandler{

    private static final String QUERYDEFINEINFO = "class com..cloud.datacenter.entity.nquery.QueryDefineInfo";
    private static final String LMORDERFIELD = "class com..cloud.datacenter.query.LmOrderField";


    @Override
    public void doInit(String fieldGenericType, Class<?> metaClass, Object newInstance, JSONObject docJson, String key) {
         if (fieldGenericType.equals(QUERYDEFINEINFO)){
             //处理queryDefineInfo对象
             //operateQueryDefineInfoObject(key,metaClass,docJson,newInstance,QueryDefineInfo.class);
         }else if (fieldGenericType.equals(LMORDERFIELD)){
             //处理LmOrderField对象
             //oprateLmOrderFieldObject(key,metaClass,docJson,newInstance,LmOrderField.class);
         }else{
             if (getNext() != null){
                 getNext().doInit(fieldGenericType,metaClass,newInstance,docJson,key);
             }else{
                 throw new RuntimeException(fieldGenericType+"属性没有职责链处理，请添加");
             }
         }
    }


    /**
     * 处理queryDefineInfo对象
     */
    private void operateQueryDefineInfoObject(String key, Class<?> metaClass, JSONObject docJson, Object newInstance, Class<?> turnClass){
        try {
            String methodName = "set" + key.substring(0,1).toUpperCase() + key.substring(1);
            Method method = metaClass.getMethod(methodName, turnClass);
            ReflectUtil reflectUtil = new ReflectUtil();
            if (docJson.containsKey(key)){
                String s = docJson.get(key).toString();
                if (s.contains("_class")){
                    JSONObject queryDefineInfoJson = JSONObject.parseObject(s);
                    try {
                        String fieldClassName = queryDefineInfoJson.get("_class").toString();
                        Class<?> childClass = Class.forName(fieldClassName);
                        Class<?> superclass = childClass.getSuperclass();
                        //初始化父类对象
                        Object fatherObject = reflectUtil.initObject(superclass, queryDefineInfoJson.toJSONString());
                        //初始化子类对象
                        Object childObject = reflectUtil.initObject(childClass, queryDefineInfoJson.toJSONString());
                        //将父类对象的属性赋给子类对象
                        fatherToChild(fatherObject,childObject);
                        method.invoke(newInstance,childObject);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理LmOrderField对象
     */
    private void oprateLmOrderFieldObject(String key, Class<?> metaClass, JSONObject docJson, Object newInstance, Class<?> turnClass){
        try {
            String methodName = "set" + key.substring(0,1).toUpperCase() + key.substring(1);
            Method method = metaClass.getMethod(methodName, turnClass);
            ReflectUtil reflectUtil = new ReflectUtil();
            if (docJson.containsKey(key)){
                String s = docJson.get(key).toString();
                JSONObject queryDefineInfoJson = JSONObject.parseObject(s);
                try {
                    Class<?> childClass = null ;//= LmOrderField.class;
                    //初始化对象
                    Object childObject = reflectUtil.initObject(childClass, queryDefineInfoJson.toJSONString());
                    method.invoke(newInstance,childObject);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    /**
     * 父类对象属性赋值给子类对象
     * @param father
     * @param child
     */
    private void fatherToChild(Object father, Object child){
        if (child.getClass().getSuperclass()!=father.getClass()){
            try {
                throw new CloneNotSupportedException(child.getClass().getName()+"不是"+father.getClass().getName()+"的子类");
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }else{
            Class<?> fatherClass = father.getClass();
            Field[] fatherFields = fatherClass.getDeclaredFields();
            for (Field field : fatherFields){
                String fieldName = field.getName();
                String methodName = null;
                if (field.getType().getName().equals("boolean")){
                    methodName = "is"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                }else{
                    methodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                }
                try {
                    Method method = fatherClass.getDeclaredMethod(methodName);
                    Object obj = method.invoke(father);
                    field.setAccessible(true);
                    field.set(child,obj);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
