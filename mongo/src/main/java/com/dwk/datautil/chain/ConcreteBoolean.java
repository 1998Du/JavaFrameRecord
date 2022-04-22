package com.dwk.datautil.chain;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description:
 *  处理boolean类型的属性
 * @author: mushi
 * @Date: 2021/4/8 10:01
 */
public class ConcreteBoolean extends ChainHandler {

    private static final String TYPE = "boolean";

    @Override
    public void doInit(String fieldGenericType, Class<?> metaClass, Object newInstance, JSONObject docJson, String key) {
        if (fieldGenericType.equals(TYPE)){
            try {
                String methodName = "set" + key.substring(0,1).toUpperCase() + key.substring(1);
                Method method = metaClass.getMethod(methodName, boolean.class);
                if (docJson.containsKey(key)){
                    //给属性key赋值 docJson.get(key));
                    boolean value = Boolean.parseBoolean(docJson.get(key).toString());
                    method.invoke(newInstance, value);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }else{
            if (getNext() != null){
                getNext().doInit(fieldGenericType,metaClass,newInstance,docJson,key);
            }else{
                throw new RuntimeException(fieldGenericType+"属性没有职责链处理，请添加");
            }
        }
    }
}
