package com.demo.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)//什么时候使用该注解，我们定义为运行时
@Target({ElementType.METHOD})//注解用于什么地方，我们定义为作用于方法上
@Documented//注解是否将包含在 JavaDoc 中
public @interface Log {//注解名为 WebLog

    /**
     * 日志描述信息
     *
     * @return
     */
    String title() default "";//定义一个属性，默认为空字符串
}
