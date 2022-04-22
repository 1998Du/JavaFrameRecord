package com.dwk.datautil.chain;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *   处理String类型属性
 * @author: mushi
 * @Date: 2021/4/8 9:46
 */
public class ConcreteString extends ChainHandler {

    private static final String TYPE = "class java.lang.String";

    @Override
    public void doInit(String fieldGenericTypem, Class<?> metaClass, Object newInstance, JSONObject docJson, String key) {
        if (fieldGenericTypem.equals(TYPE)){
            //处理String类型的属性
            try {
                String methodName = "set" + key.substring(0,1).toUpperCase() + key.substring(1);
                Method method = metaClass.getMethod(methodName, String.class);
                if (("id").equalsIgnoreCase(key)){
                    key = "_id";
                }
                if (docJson.containsKey(key)){
                    //给属性key赋值 docJson.get(key));
                    String value = docJson.get(key).toString();
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
                getNext().doInit(fieldGenericTypem,metaClass,newInstance,docJson,key);
            }else{
                throw new RuntimeException(fieldGenericTypem+"属性没有职责链处理，请添加");
            }
        }
    }
}
