package com.cartisan.aops;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static com.cartisan.utils.RequestHolder.getHttpServletRequest;

/**
 * @author colin
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    @Pointcut("execution(public * *.*Controller.*(..))")
    public void cut() {

    }

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest request = getHttpServletRequest();

        log.info("@@@ API Request - { url: {}, httpMethod: {}, ip: {}, classMethod:{}, args:{} } @@@",
                request.getRequestURL().toString(),
                request.getMethod(),
                request.getRemoteAddr(),
                joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

//    @AfterReturning(returning = "obj", pointcut = "cut()")
//    public void afterReturning(Object obj) {
//        log.info("@@@ API Response - {} @@@", JSON.toJSONString(obj));
//    }

    @Around("cut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object obj = proceedingJoinPoint.proceed();
        log.info("耗时：{}", System.currentTimeMillis()-startTime);
        return obj;
    }
}
