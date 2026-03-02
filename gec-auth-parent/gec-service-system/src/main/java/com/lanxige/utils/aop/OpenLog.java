package com.lanxige.utils.aop;

import com.lanxige.eurm.BusinessType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * &#064;Description:  自定义注解，用于记录日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OpenLog {

    String title();

    BusinessType businessType() default BusinessType.OTHER;

    String requestMethod() default ""; // 手动写 POST / GET
}