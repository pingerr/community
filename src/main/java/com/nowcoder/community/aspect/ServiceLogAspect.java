package com.nowcoder.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect // Service层，统一记录日志
public class ServiceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    // 1、定义切点(代码织入点)：织入哪些Bean？织入Bean的哪些位置
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    // 第一个*代表返回值，第二个*代表组件，第三个*代表方法，(..)代表所有的参数
    // 所有的service组件，及其方法、参数、返回值都要处理
    public void pointcut() {

    }

    // 2、定义通知
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        // 用户{1，2，3，4}，在{xxx(时间)}，访问了{com.nowcoder.community.service.xxx()}.
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteHost();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String target = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        logger.info(String.format("用户[%s], 在[%s],访问了[%s].", ip, now, target));
    }

}
