package com.quasar.backend.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class HttpLog {

    /**
     * 定义AOP扫描路径
     * 第一个注解只扫描aopTest方法
     */
    @Pointcut("execution(public * com.quasar.backend.controller.*.*())")
    public void log() {
    }

    /**
     * 记录HTTP请求开始的参数
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //URL
        log.info("url={}", request.getRequestURI());
        //method
        log.info("method={}", request.getMethod());
        //ip
        log.info("ip={}", request.getRemoteAddr());
        //类方法
        log.info("class={} and method name = {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        //参数
        log.info("参数={}", joinPoint.getArgs());
    }

    /**
     * 记录HTTP请求结束时的日志
     */
    @After("log()")
    public void doAfter() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("url = {} end of execution", request.getRequestURL());
    }

    /**
     * 获取返回内容
     *
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturn(Object object) {
        log.info("response={}", object.toString());
    }
}
