package com.nowcoder.community.controller.advice;

import com.nowcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice(annotations = Controller.class) // 扫描所有带有Controller注解的Bean
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    // 统一异常处理
    @ExceptionHandler({Exception.class})  // 处理所有异常的方法
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器发生异常：" + e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            logger.error(element.toString());
        }

        // 判断是普通请求还是异步请求
        String xRequestedWith = request.getHeader("x-requested-with");
        if ("XMLHttpRequest".equals(xRequestedWith)) {
            // 异步请求，响应JSON
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJsonString(1, "服务器异常！"));
        } else {
            // 普通请求，重定向页面
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

}
