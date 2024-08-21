package com.dwk.annotation;

import java.lang.annotation.*;

/**
 * @author duweikun
 * @date 2023/7/19  10:01
 * @description   数据脱敏类注解
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataMaskClass {

}
