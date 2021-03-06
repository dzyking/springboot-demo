package com.demo.entity;

/**
 * 返回码
 */
public class StatusCode {

    public static final int OK = 200;// 成功
    public static final int ERROR = 500;// 服务器内部错误
    public static final int LOGINERROR = 202;// 用户名或密码错误
    public static final int ACCESSERROR = 401;// 权限不足
    public static final int REMOTEERROR = 204;// 远程调用失败
    public static final int REPERROR = 205;// 重复操作
    public static final int PARAMETERCHECKERROR = 206;// 参数校验异常
}
