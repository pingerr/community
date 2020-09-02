package com.nowcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //作用范围：方法上
@Retention(RetentionPolicy.RUNTIME) //作用时间
public @interface LoginRequired {

}
