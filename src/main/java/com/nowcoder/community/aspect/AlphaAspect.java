package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect // 切面组件
public class AlphaAspect {

    // 1、定义切点(代码织入点)：织入哪些Bean？织入Bean的哪些位置
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    // 第一个*代表返回值，第二个*代表组件，第三个*代表方法，(..)代表所有的参数
    // 所有的service组件，及其方法、参数、返回值都要处理
    public void pointcut() {

    }

    // 2、定义通知（有5种）
    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }

    @After("pointcut()")
    public void after() {
        System.out.println("after");
    }

    @AfterReturning("pointcut()") // 返回值后织入
    public void afterReturning() {
        System.out.println("afterReturning");
    }

    @AfterThrowing("pointcut()") // 抛异常后织入
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    @Around("pointcut()") // 前后都织入
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("around before");
        Object obj = joinPoint.proceed(); // 目标组件的织入点（方法）
        System.out.println("around after");
        return obj;
    }
}
