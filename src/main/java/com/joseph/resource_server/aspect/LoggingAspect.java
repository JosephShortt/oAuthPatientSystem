package com.joseph.resource_server.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.joseph.resource_server.service.*.*(..))")
    public Object logServiceMethods(ProceedingJoinPoint jointPoint) throws Throwable{
        String methodName = jointPoint.getSignature().getName();
        Object[] args = jointPoint.getArgs();

        logger.info(">>> Calling method: {} with args: {}", methodName, Arrays.toString(args));

        long start = System.currentTimeMillis();
        Object result = jointPoint.proceed();
        long duration = System.currentTimeMillis() - start;

        logger.info("<<< Method: {} completed in {}ms", methodName, duration);

        return result;
    }
}
