package com.dwk.plugin;

import com.dwk.annotation.DataMaskClass;
import com.dwk.annotation.DataMaskColumn;
import com.dwk.util.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

/**
 * @author duweikun
 * @date 2023/7/18  14:50
 * @description  数据脱敏插件,拦截特定表字段进行加解密
 * 基于sql级别拦截，并非所有逻辑都生效  具体业务还需具体处理
 */
@Slf4j
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class DataMaskResultPlugin implements Interceptor {

    private Properties properties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //取出查询的结果
        Object resultObject = invocation.proceed();
        if (Objects.isNull(resultObject)) {
            return null;
        }
        //基于selectList
        if (resultObject instanceof ArrayList) {
            ArrayList resultList = (ArrayList) resultObject;
            if (!CollectionUtils.isEmpty(resultList) && needToDecrypt(resultList.get(0))) {
                for (Object result : resultList) {
                    //逐一解密
                    decrypt(result);
                }
            }
            //基于selectOne
        }else {
            if (needToDecrypt(resultObject)) {
                decrypt(resultObject);
            }
        }
        return resultObject;
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * 单个结果集判空
     * @param object
     * @return
     */
    private boolean needToDecrypt(Object object) {
        Class<?> objectClass = object.getClass();
        DataMaskClass dataMaskClass = AnnotationUtils.findAnnotation(objectClass, DataMaskClass.class);
        return Objects.nonNull(dataMaskClass);
    }

    /**
     * 解密
     * @return
     */
    public Object decrypt(Object result){
        //取出resultType的类
        Class<?> resultClass = result.getClass();
        Field[] declaredFields = resultClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            DataMaskColumn dataMaskColumn = declaredField.getAnnotation(DataMaskColumn.class);
            if (!Objects.isNull(dataMaskColumn)) {
                declaredField.setAccessible(true);
                //这里的result就相当于是字段的访问器
                try {
                    Object object = declaredField.get(result);
                    //只支持String解密
                    if (object instanceof String) {
                        String value = (String) object;
                        //修改：没有标识则不解密
                        value = AesUtil.decode(value);
                        //对注解在这段进行逐一解密
                        declaredField.set(result, StringUtils.isEmpty(value) || ("null").equals(value) ? "" : value);
                    }
                }catch (Exception e){
                    log.error("字段解密失败：{}", declaredField);
                }
            }
        }
        return result;
    }


}
