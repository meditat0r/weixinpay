package com.kuangstudy.weixinpay.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName IgnoreToken
 * @Author ：xuke
 * @Date ：2021/1/16 22:30
 * @Description：
 * @Version: 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreToken {
    boolean required() default true;
}
