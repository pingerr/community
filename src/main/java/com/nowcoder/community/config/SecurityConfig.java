package com.nowcoder.community.config;

import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements CommunityConstant {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略对静态资源的拦截
        web.ignoring().antMatchers("/resources/**");
    }

    // 绕过其默认的认证，但需要将权限加到其中，在UserService中添加


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 授权
        http.authorizeRequests()
                .antMatchers(
                    "/user/setting",
                        "/user/upload",
                        "/discuss/add",
                        "/comment/add",
                        "/letter/**",
                        "/like"
                )
                .hasAnyAuthority(
                    AUTHORITY_USER,
                    AUTHORITY_ADMIN,
                    AUTHORITY_MODERATOR
                )
                .anyRequest().permitAll() // 其他页面所有权限都可以
                .and()
                .csrf().disable(); // 不启用csrf，否则所有Ajax请求都得处理csrf_token,否则视为攻击

        // 权限不够时处理， 返回JSON
        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    // 没有登录时的处理
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                        String xRequestedWith = request.getHeader("x-requested-with");
                        if ("XMLHttpRequest".equals(xRequestedWith)) {
                            // 异步请求，返回JSON
                            response.setContentType("application/plain;charset=utf8");
                            PrintWriter writer = response.getWriter();
                            writer.write(CommunityUtil.getJsonString(403, "你还没有登录喔！"));

                        } else {
                            response.sendRedirect(request.getContextPath() + "/login");
                        }
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                        String xRequestedWith = request.getHeader("x-requested-with");
                        if ("XMLHttpRequest".equals(xRequestedWith)) {
                            // 异步请求，返回JSON
                            response.setContentType("application/plain;charset=utf8");
                            PrintWriter writer = response.getWriter();
                            writer.write(CommunityUtil.getJsonString(403, "你没有访问此功能的权限！"));

                        } else {
                            response.sendRedirect(request.getContextPath() + "/denied");
                        }
                    }
                });

        // Security 底层默认拦截/logout请求，进行退出请求。
        // 覆盖其默认的逻辑(欺骗)，才能执行我们自己的退出代码
        http.logout().logoutUrl("/securityLogout");
    }
}
