package tn.esprit.banking.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;

@Slf4j
public class LoggingAspect {

    private static final Logger log = LogManager.getLogger(LoggingAspect.class);

    @Before("execution(* tn.esprit.banking.service.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.info("In method " + name + " : ");
    }
    @AfterReturning("execution(* tn.esprit.banking.service.*.*(..))")
    public void logMethodExit1(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.info("Out of method without errors " + name );
    }

    @AfterThrowing("execution(* tn.esprit.banking.service.*.*(..))")
    public void logMethodExit2(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.info("Out of method with errors " + name );
    }

    @After("execution(* tn.esprit.banking.service.*.*(..))")
    public void logMethodExit(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.info("Out of method " + name );
    }


}
