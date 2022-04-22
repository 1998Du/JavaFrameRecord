package com.dwk.datautil.chain;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Description:
 *
 * @author: mushi
 * @Date: 2021/4/9 16:04
 */
public class ConcreteMap extends ChainHandler{

    private static final String MAPTYPE = "java.util.Map<java.lang.String, java.lang.String>";

    @Override
    public void doInit(String fieldGenericType, Class<?> metaClass, Object newInstance, JSONObject docJson, String key) {
        if (fieldGenericType.equals(MAPTYPE)){
            try {
                String methodName = "set" + key.substring(0,1).toUpperCase() + key.substring(1);
                Method method = metaClass.getMethod(methodName, Map.class);
                if (docJson.containsKey(key)){
                    //给属性key赋值 docJson.get(key));
                    Map value = (Map) docJson.get(key);
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
