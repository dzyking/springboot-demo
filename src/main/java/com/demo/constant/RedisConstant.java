package com.demo.constant;


public class RedisConstant {
    /**
     * 注册
     */
    public static final Integer REGISTER_SMSTYPE = 1;
    public static final String REGISTER_SMSTID = "155";
    public static final String REGISTER_SMS_REDISNAME = "smscode:RegisterCode_";
    /**
     * 登录
     */
    public static final Integer LOGIN_SMSTYPE = 2;
    public static final String LOGIN_SMSTID = "156";
    public static final String LOGIN_SMS_REDISNAME = "smscode:Login_";
    /**
     * token
     */
    public static final String TOKEN_NAME = "token:token_";
    public static final Integer TOKEN_TIME = 2 * 24 * 60 * 60;
}
