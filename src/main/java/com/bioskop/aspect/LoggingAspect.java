package com.bioskop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    
    // Pointcut for all service methods
    @Pointcut("execution(* com.bioskop.service.*.*(..))")
    public void serviceLayer() {}
    
    // Pointcut for all controller methods
    @Pointcut("execution(* com.bioskop.controller.*.*(..))")
    public void controllerLayer() {}
    
    // Pointcut for all DAO methods
    @Pointcut("execution(* com.bioskop.dao.*.*(..))")
    public void daoLayer() {}
    
    @Before("serviceLayer()")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        logger.info("BEFORE SERVICE: {}.{} - Argumenti: {}", 
                   className, methodName, Arrays.toString(args));
    }
    
    @After("serviceLayer()")
    public void logAfterServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        logger.info("AFTER SERVICE: {}.{} - Metoda završena", className, methodName);
    }
    
    @Before("controllerLayer()")
    public void logBeforeControllerMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        logger.info("BEFORE CONTROLLER: {}.{} - HTTP zahtev sa argumentima: {}", 
                   className, methodName, Arrays.toString(args));
    }
    
    @After("controllerLayer()")
    public void logAfterControllerMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        logger.info("AFTER CONTROLLER: {}.{} - HTTP odgovor poslat", className, methodName);
    }
    
    @Around("serviceLayer()")
    public Object logAroundServiceMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = proceedingJoinPoint.proceed();
            
            long endTime = System.currentTimeMillis();
            logger.info("AROUND SERVICE: {}.{} - Uspešno izvršena za {} ms", 
                       className, methodName, (endTime - startTime));
            
            return result;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            logger.error("AROUND SERVICE: {}.{} - Greška nakon {} ms: {}", 
                        className, methodName, (endTime - startTime), e.getMessage());
            throw e;
        }
    }
    
    @AfterReturning(pointcut = "daoLayer()", returning = "result")
    public void logAfterReturningDAO(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        if (result != null) {
            logger.debug("DAO RETURN: {}.{} - Vraćen rezultat tipa: {}", 
                        className, methodName, result.getClass().getSimpleName());
        } else {
            logger.debug("DAO RETURN: {}.{} - Vraćen null rezultat", className, methodName);
        }
    }
    
    @AfterThrowing(pointcut = "serviceLayer() || controllerLayer()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        logger.error("EXCEPTION: {}.{} - Izuzetak: {} - Poruka: {}", 
                    className, methodName, exception.getClass().getSimpleName(), exception.getMessage());
    }
}