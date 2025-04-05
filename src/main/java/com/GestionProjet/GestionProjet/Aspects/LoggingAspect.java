package com.GestionProjet.GestionProjet.Aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // Pointcut for all methods in the Service.Impl package
    @Pointcut("execution(* com.GestionProjet.GestionProjet.Services.Impl.*.*(..))")
    public void serviceMethods() {}

    // Before any method execution
    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("User: {} is calling method: {} with arguments: {}",
                getCurrentUser(), joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    // After method returns successfully
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("User: {} successfully completed method: {} with result: {}",
                getCurrentUser(), joinPoint.getSignature().toShortString(), result);
    }

    // If method throws exception
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("User: {} caused exception in method: {} - Message: {}",
                getCurrentUser(), joinPoint.getSignature().toShortString(), ex.getMessage());
    }

    // Specifically log DELETE methods
    @After("execution(* com.GestionProjet.GestionProjet.Services.Impl.*.delete*(..))")
    public void logAfterDelete(JoinPoint joinPoint) {
        log.warn("User: {} performed DELETE operation in method: {}",
                getCurrentUser(), joinPoint.getSignature().toShortString());
    }

    // Get current user from Spring Security context
    private String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated()) ? auth.getName() : "Anonymous";
    }
}
