package com.bioskop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * AOP Logging aspect that uses only @After pointcuts.
 * This aspect logs method executions after they complete, whether successful or with exceptions.
 */
@Aspect
@Component
public class LoggingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    
    /**
     * Logs after any method execution in service packages (successful or failed)
     */
    @After("execution(* com.bioskop.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("Method execution completed: {}.{}", className, methodName);
    }
    
    /**
     * Logs after successful method execution with return value
     */
    @AfterReturning(pointcut = "execution(* com.bioskop.service.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("Method executed successfully: {}.{}, returned: {}", className, methodName, result);
    }
    
    /**
     * Logs after method execution that throws an exception
     */
    @AfterThrowing(pointcut = "execution(* com.bioskop.service.*.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.error("Method threw exception: {}.{}, exception: {}", className, methodName, exception.getMessage());
    }
    
    /**
     * Logs after any controller method execution
     */
    @After("execution(* com.bioskop.controller.*.*(..))")
    public void logControllerAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("Controller method execution completed: {}.{}", className, methodName);
    }
}