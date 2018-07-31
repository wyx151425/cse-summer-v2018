package com.cse.summer.context.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 应用日志切面
 *
 * @author 王振琦
 */
@Aspect
@Component
public class SummerAspect {

    private Logger logger = LoggerFactory.getLogger(SummerAspect.class);

    @Pointcut("execution(public * com.cse.summer..*.*(..))")
    public void machinePointcut() {
    }

    @Pointcut("execution(public * com.cse.summer.controller.*.*(..))")
    public void controllerPointcut() {
    }

    @Before(value = "machinePointcut()")
    public void actionBefore(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName()
                + ": " + joinPoint.getSignature().getName() + "-start");

        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        request.getRequestURL();
        request.getMethod();
        request.getRemoteAddr();
    }

    @After(value = "machinePointcut()")
    public void actionAfter(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName()
                + ": " + joinPoint.getSignature().getName() + "-stop");
    }

    @AfterReturning(value = "machinePointcut()", returning = "object")
    public void actionAfterReturning(JoinPoint joinPoint, Object object) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName()
                + ": " + joinPoint.getSignature().getName() + "-returning");
        logger.info("Return Value: " + object);
    }

    @AfterThrowing(value = "controllerPointcut()", throwing = "ex")
    public void actionAfterThrowing(JoinPoint joinPoint, Exception ex) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName()
                + ": " + joinPoint.getSignature().getName() + "-throwing");
        logger.error("Throw Exception: ", ex);
    }
}
