package com.demo.constant;

/**
 * @Description :redis相关常量
 */
public class RedisConstant {
    /**
     * 注册验证码
     */
    public static final int REGISTER_SMSTYPE = 1;
    public static final String REGISTER_SMSTID = "155";
    public static final String REGISTER_SMS_REDISNAME = "smscode:RegisterCode_";
    /**
     * 登录验证码
     */
    public static final int LOGIN_SMSTYPE = 2;
    public static final String LOGIN_SMSTID = "156";
    public static final String LOGIN_SMS_REDISNAME = "smscode:Login_";
    /**
     * 修改密码
     */
    public static final int CHANGE_PASSWORD_SMSTYPE = 3;
    public static final String CHANGE_PASSWORD_SMSTID = "185";
    public static final String CHANGE_PASSWORD_SMS_REDISNAME = "smscode:ChangePassword_";
    /**
     * 修改手机号
     */
    public static final int CHANGE_PHONE_SMSTYPE = 4;
    public static final String CHANGE_PHONE_SMSTID = "186";
    public static final String CHANGE_PHONE_SMS_REDISNAME = "smscode:ChangePHONE_";
    /**
     * 其他验证码
     */
    public static final String OTHER_SMSTID = "162";
    public static final String OTHER_SMS_REDISNAME = "smscode:Other_";
    /**
     * 修改密码
     */
    public static final String CAN_CHANGE_PASSWORD_NAME = "changePassword:phone_";
    public static final Integer CAN_CHANGE_PASSWORD_TIME = 10 * 60;
    /**
     * 短信存放秒数
     */
    public static final Integer SMS_SAVE_TIME = 5 * 60;
    /**
     * token名称及存放时间
     */
    public static final String TOKEN_NAME = "token:token_";
    public static final Integer TOKEN_TIME = 5 * 24 * 60 * 60;
    //续租时间
    public static final Integer TOKEN_RENEWAL_TIME = 2 * 24 * 60 * 60;
    /**
     * 省市区token
     */
    public static final String AREA_ALL_NAME = "area:allAreas";
    public static final String AREA_PROVINCE_LIST_NAME = "area:listProvince";
    public static final String AREA_CITY_LIST_NAME = "area:province:province_";
    public static final String AREA_TOWN_LIST_NAME = "area:city:city_";

}
