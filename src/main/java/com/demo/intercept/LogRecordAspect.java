package com.demo.intercept;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName :LogRecordAspect
 * @Description : 定义一个切面 使用AOP打印接口请求信息,输入输出
 */
@Aspect
@Configuration
@Slf4j
public class LogRecordAspect {

    // 定义切点Pointcut
    @Pointcut("execution(* com.demo.*Controller.*(..))")
    public void executionService() {
    }

    /**
     * @Description : 环绕方法,从请求开始到结束
     */
    @Around("executionService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String method = request.getMethod();//请求方式为GET/POST
        String methodName = pjp.getSignature().getName();//请求controller层里面的方法名
        String controller = pjp.getTarget().getClass().getName();//请求的是哪个controller
        String uri = request.getRequestURI();//请求接口名
        log.info("SpringBoot action report -------- " + getDate() + " ------------------------------");
        log.info("Uri         : " + uri);
        log.info("Controller  : " + controller + getTargetStack(controller));
        log.info("Method      : " + method);
        log.info("MethodName  : " + methodName);
        log.info("Params      : " + Arrays.toString(pjp.getArgs()));
        Object result = pjp.proceed();//获得拦截方法的返回值
        log.info("Result      : " + result);
        long time = System.currentTimeMillis() - startTime;// 执行耗时
        log.info("Time        : " + time + "ms");
        log.info("------------------------------------------------------------------------------------");
        System.out.print("\n");
        return result;
    }

    /**
     * @Description : 抛出异常时打印异常信息
     */
    @AfterThrowing(throwing = "e", value = "executionService()")
    public void  doAfterThrowing(Throwable e) {
        log.info("Exception   : " + e.getMessage());
        log.info("------------------------------------------------------------------------------------");
        System.out.print("\n");
    }

    //用StringBuffer定位调用的controller层 定位第一行
    private static String getTargetStack(String tag) {
        StringBuilder sb = new StringBuilder();
        int number = tag.lastIndexOf(".");//找到controller名称中最后一个.的索引
        String controller = tag.substring(number + 1);//截取controller的名字
        sb.append(".(").append(controller).append(".java:1)");//定位在controller类里面的第一行
        return sb.toString();
    }

    //获取当前时间
    private String getDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

}

