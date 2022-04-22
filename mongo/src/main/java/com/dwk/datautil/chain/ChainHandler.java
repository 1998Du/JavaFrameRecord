package com.dwk.datautil.chain;

import com.alibaba.fastjson.JSONObject;

/**
 * Description:
 *
 * @author: mushi
 * @Date: 2021/4/8 9:40
 */
public abstract class ChainHandler {

    private ChainHandler next;

    /**
     *
     * @param fieldGenericType 属性类型
     * @param metaClass 类
     * @param newInstance 实例
     * @param docJson json格式文档数据
     * @param key 文档key
     */
    public abstract void doInit(String fieldGenericType, Class<?> metaClass, Object newInstance, JSONObject docJson, String key);

    public ChainHandler getNext() {
        return next;
    }

    public void setNext(ChainHandler next) {
        this.next = next;
    }
}
