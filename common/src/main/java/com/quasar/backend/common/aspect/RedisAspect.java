package com.quasar.backend.common.aspect;

import com.quasar.backend.common.exception.RRException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Redis切面处理类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-17 23:30
 */
@Slf4j
@Aspect
@Component
public class RedisAspect {
    //是否开启redis缓存  true开启   false关闭
    @Value("${srv.redis.open: false}")
    private boolean open;

    @Around("execution(* com.quasar.backend.common.utils.RedisUtils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        if(open){
            try{
                result = point.proceed();
            }catch (Exception e){
                log.error("redis error", e);
                throw new RRException("Redis服务异常");
            }
        }
        return result;
    }
}
