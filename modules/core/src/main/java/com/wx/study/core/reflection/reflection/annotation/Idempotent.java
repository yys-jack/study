package com.wx.study.core.reflection.annotation;

import java.lang.annotation.*;

/**
 * @author wxli
 * @date 2021/7/12 17:48
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {

    long expiredTime() default 10L;

}
