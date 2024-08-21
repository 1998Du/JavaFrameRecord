package com.dwk.plugin;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dwk.annotation.DataMaskClass;
import com.dwk.annotation.DataMaskColumn;
import com.dwk.config.DataConfig;
import com.dwk.util.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Properties;

/**
 * @author duweikun
 * @date 2023/7/18  14:50
 * @description 数据脱敏插件, 拦截特定表字段进行加解密
 * 基于sql级别拦截，并非所有逻辑都生效  具体业务还需具体处理
 */
@Slf4j
@Intercepts({@Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class)})
public class DataMaskParamPlugin implements Interceptor {

    private Properties properties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
        //获取参数对象，即mapper中paramsType的实例
        Field paramsFiled = parameterHandler.getClass().getDeclaredField("parameterObject");
        paramsFiled.setAccessible(true);
        //取出实例
        Object parameterObject = paramsFiled.get(parameterHandler);
        if (parameterObject != null) {
            Class<?> parameterObjectClass = parameterObject.getClass();
            //校验该实例的类是否被@SensitiveData所注解
            DataMaskClass dataMaskClass = AnnotationUtils.findAnnotation(parameterObjectClass, DataMaskClass.class);
            if (Objects.nonNull(dataMaskClass)) {
                //取出当前类的所有字段，传入加密方法
                Field[] declaredFields = parameterObjectClass.getDeclaredFields();
                encrypt(declaredFields, parameterObject);
            }
        }
        return invocation.proceed();
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }


    /**
     * 对添加了注解的字段进行加密
     */
    public Object encrypt(Field[] declaredFields,Object paramsObject){
        try {
            for (Field field : declaredFields) {
                //取出所有被EncryptDecryptFiled注解的字段
                DataMaskColumn filed = field.getAnnotation(DataMaskColumn.class);
                if (!Objects.isNull(filed)) {
                    field.setAccessible(true);
                    Object object = field.get(paramsObject);
                    // 加密
                    try{
                        String value = String.valueOf(object);
                        if (!value.startsWith(DataConfig.KEY_PREFIX)){
                            value = AesUtil.encode(value);
                        }
                        field.set(paramsObject,value);
                    }catch (Exception e){
                        // 其他类型先转JSON后转String
                        try {
                            JSONObject jsonObject = JSONUtil.parseObj(object);
                            String value = jsonObject.toString();
                            value = AesUtil.encode(value);
                            field.set(paramsObject,value);
                        }catch (Exception exception){
                            log.error("字段加密失败：{}",paramsObject.toString());
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return paramsObject;
    }

}
