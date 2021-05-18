package com.demo.intercept.logs;

import lombok.Data;

import java.util.Date;

@Data
public class SystemLog {
    /**
     * 日志主键
     */
    private Long operId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 日志来源
     */
    private String source;

    /**
     * 操作模块
     */
    private String title;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求url
     */
    private String operUrl;

    /**
     * 操作地址
     */
    private String operIp;

    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 状态0正常 1异常
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    private Date operTime;
}
