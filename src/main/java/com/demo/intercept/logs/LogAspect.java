package com.demo.intercept.logs;

import com.alibaba.fastjson.JSONObject;
import com.demo.util.ServletUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    // 配置织入点
    @Pointcut("@annotation(com.demo.intercept.logs.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        handleLog(joinPoint, jsonResult, attributes);
    }

    /**
     * 环绕
     */
    @Around(value = "execution(public * com.demo.ctrl.*.*(..))")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 执行耗时
        log.info("请求耗时 : {} ms", System.currentTimeMillis() - startTime);
        return result;
    }

    protected void handleLog(final JoinPoint joinPoint, Object jsonResult, ServletRequestAttributes attributes) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            // *========数据库日志=========*//
            SystemLog systemLog = new SystemLog();
            systemLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            systemLog.setOperTime(new Date());
            // 请求的地址
            String ip = request.getRemoteAddr();
            systemLog.setOperIp(ip);
            if (jsonResult != null) {
                systemLog.setJsonResult(jsonResult.toString());
            }

            systemLog.setOperUrl(ServletUtil.getRequest().getRequestURI());

            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            systemLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            systemLog.setRequestMethod(ServletUtil.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, systemLog);
            log.info("请求方法:{}\r\n请求参数:{}\r\n返回结果:{}", systemLog.getTitle() + " " + systemLog.getMethod(),
                    systemLog.getOperParam(), systemLog.getJsonResult());
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==日志记录返回结果异常==", exp);
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param systemLog 操作日志
     */
    public void getControllerMethodDescription(Log log, SystemLog systemLog){
        // 设置标题
        systemLog.setTitle(log.title());
        // 设置操作人类别
        // 是否需要保存request，参数和值
        // 获取参数的信息，传入到数据库中。
        setRequestValue(systemLog);
    }

    /**
     * 获取请求的参数，放到log中
     *
     */
    private void setRequestValue(SystemLog systemLog) {
        Map<String, Object> map = new HashMap<>();
        Enumeration<String> parameterNames = ServletUtil.getRequest().getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = ServletUtil.getRequest().getParameter(paramName);
            // 形成键值对应的map
            map.put(paramName, paramValue);
        }
        String params = JSONObject.toJSONString(map);
        systemLog.setOperParam(params);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
