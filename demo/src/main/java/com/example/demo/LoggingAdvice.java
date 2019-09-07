package com.example.demo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Aspect
@Component
public class LoggingAdvice {
    private final Logger logger;

    public LoggingAdvice() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Before("execution(* com.example.demo.controller.*.*(..))")
    public void before(JoinPoint joinPoint) {
        outputLog(joinPoint);
    }

    @AfterReturning(pointcut = "execution(* com.example.demo.controller.*.*(..))", returning = "returnValue")
    public void after(JoinPoint joinPoint, Object returnValue) {
        outputLog(joinPoint, returnValue);
    }
    @AfterThrowing(value = "execution(* com.example.demo.controller.*.*(..))", throwing = "e")
    public void invokeControllerAfterThrowing(JoinPoint joinPoint, Throwable e) {
        outputErrorLog(joinPoint, e);
    }

    private void outputLog(JoinPoint joinPoint) {
        String logMessage = "[" + getSessionId() + "]:" + getClassName(joinPoint) + "." + getSignatureName(joinPoint) + ":START:" + getArguments(joinPoint);
        logger.info(logMessage);
    }

    private void outputLog(JoinPoint joinPoint, Object returnValue) {
        String logMessage = "[" + getSessionId() + "]:" + getClassName(joinPoint) + "." + getSignatureName(joinPoint) + ":END:" + getReturnValue(returnValue);
        logger.info(logMessage);
    }

    private void outputErrorLog(JoinPoint joinPoint, Throwable e) {
        String logMessage = "[" + getSessionId() + "]:" + getClassName(joinPoint) + "." + getSignatureName(joinPoint) + ":arguments:" + getArguments(joinPoint);
        logger.error(logMessage, e);
    }

    private String getSessionId() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getId();
    }
    private String getClassName(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().toString();
    }

    private String getSignatureName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    private String getArguments(JoinPoint joinPoint) {
        if (joinPoint.getArgs() == null) {
            return "argument is null";
        }

        Object[] arguments = joinPoint.getArgs();
        ArrayList<String> argumentStrings = new ArrayList();

        for (Object argument : arguments) {
            if (argument != null) {
                argumentStrings.add(argument.toString());
            }
        }
        return String.join(",", argumentStrings);
    }

    private String getReturnValue(Object returnValue) {
        return (returnValue != null) ? returnValue.toString() : "return value is null";
    }

}
