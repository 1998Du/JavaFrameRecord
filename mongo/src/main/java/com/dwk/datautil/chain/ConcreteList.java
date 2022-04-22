package com.dwk.datautil.chain;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 *
 * @author: mushi
 * @Date: 2021/4/9 9:06
 */
public class ConcreteList extends ChainHandler{

    private static final String STRINGTYPE = "java.util.List<java.lang.String>";
    private static final String FILTERDEFINETYPE ="java.util.List<com..cloud.datacenter.entity.nquery.FilterDefine>";
    private static final String COLUMNDEFINETYPE ="java.util.List<com..cloud.datacenter.entity.nquery.ColumnDefine>";
    private static final String GROUP_STATISTIC_TYPE = "java.util.List<com..cloud.datacenter.entity.nquery.GroupStatistic>";


    @Override
    public void doInit(String fieldGenericType, Class<?> metaClass, Object newInstance, JSONObject docJson, String key) {
        if (fieldGenericType.equals(STRINGTYPE) ||
                fieldGenericType.equals(FILTERDEFINETYPE) ||
                fieldGenericType.equals(COLUMNDEFINETYPE) ||
                fieldGenericType.equals(GROUP_STATISTIC_TYPE)){
            try {
                String methodName = "set" + key.substring(0,1).toUpperCase() + key.substring(1);
                Method method = metaClass.getMethod(methodName, List.class);
                if (docJson.containsKey(key)){
                    //给属性key赋值 docJson.get(key));
                    List<Object> value = Arrays.asList(docJson.get(key));
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
