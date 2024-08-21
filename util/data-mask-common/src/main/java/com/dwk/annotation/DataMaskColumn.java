package com.dwk.annotation;

import java.lang.annotation.*;

/**
 * @author duweikun
 * @date 2023/7/19  10:02
 * @description   数据脱敏字段注解
 */
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataMaskColumn {
}
