package com.example.demo.services;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * AspectLogger
 *
 * Handles the generic logging of classes
 *
 * @author Tyler Kukkola
 */
@Aspect
@Component
public class AspectLogger {
    private static Logger logger = LogManager.getLogger(AspectLogger.class.getName());

    /***************************************************
     * Handles generic logging of EVERY controller class
     */
    @Before("execution(* com.example.demo.controllers.*Controller.*(..))")
    public void beforeController(JoinPoint p) {
        Signature sig = p.getSignature();
        logger.info("Entered Controller " + sig.getName() + " " + Arrays.toString(p.getArgs()));
    }

    @After("execution(* com.example.demo.controllers.*Controller.*(..))")
    public void afterController(JoinPoint p) {
        Signature sig = p.getSignature();
        logger.info("Returned from Controller " + sig.getName());
    }

    /***************************************************
     * Handles generic logging of EVERY controller class
     */
    @Before("execution(* com.example.demo.services.*Service.*(..))")
    public void beforeService(JoinPoint p) {
        Signature sig = p.getSignature();
        logger.info("Entered Service " + sig.getName() + " " + Arrays.toString(p.getArgs()));
    }

    @After("execution(* com.example.demo.services.*Service.*(..))")
    public void afterService(JoinPoint p) {
        Signature sig = p.getSignature();
        logger.info("Returned from Service " + sig.getName());
    }
}
