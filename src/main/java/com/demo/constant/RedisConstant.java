package com.demo.constant;

/**
 * @Description :redis相关常量
 */
public class RedisConstant {
    /**
     * 注册验证码
     */
    public static final int REGISTER_SMSTYPE = 1;
    public static final String REGISTER_SMSTID = "454";
    public static final String REGISTER_SMS_REDISNAME = "smscode:registerCode_";
    /**
     * 登录验证码
     */
    public static final int LOGIN_SMSTYPE = 2;
    public static final String LOGIN_SMSTID = "452";
    public static final String LOGIN_SMS_REDISNAME = "smscode:login_";
    /**
     * 修改密码
     */
    public static final int CHANGE_PASSWORD_SMSTYPE = 3;
    public static final String CHANGE_PASSWORD_SMSTID = "458";
    public static final String CHANGE_PASSWORD_SMS_REDISNAME = "smscode:changePassword_";
    /**
     * 修改手机号
     */
    public static final int CHANGE_PHONE_SMSTYPE = 4;
    public static final String CHANGE_PHONE_SMSTID = "457";
    public static final String CHANGE_PHONE_SMS_REDISNAME = "smscode:changePhone_";
    public static final String CHANGE_PHONE_USER_ID = "smscode:newPhone_userId_";
    /**
     * 其他验证码
     */
    public static final String OTHER_SMSTID = "456";
    public static final String OTHER_SMS_REDISNAME = "smscode:other_";
    /**
     * 不允许注册号码
     */
    public static final String NOT_ALLOW_REGISTER_NAME = "notAllowRegister:";
    public static final Integer NOT_ALLOW_REGISTER_TIME = 2 * 24 * 60 * 60;
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
    /**
     * 续租时间
     */
    public static final Integer TOKEN_RENEWAL_TIME = 2 * 24 * 60 * 60;
    /**
     * 省市区token
     */
    public static final String AREA_ALL_NAME = "area:allAreas";
    public static final String AREA_PROVINCE_LIST_NAME = "area:listProvince";
    public static final String AREA_CITY_LIST_NAME = "area:province:province_";
    public static final String AREA_TOWN_LIST_NAME = "area:city:city_";
    /**
     * 敏感词map
     */
    public static final String SENSITIVE_WORD_NAME = "sensitive:wordList";
    /**
     * 客服问答
     */
    public static final String ARTIFICIAL_QUESTIONS_ANSWERS_LIST_NAME = "question:answer_list";
    /**
     * 客服配置
     */
    public static final String ARTIFICIAL_CONFIG_NAME = "Artificial:config";

    //用户文件夹  "user/"+userId+"/headImg/first.jpg"
    //群组文件夹  "group/"+groupId+"/headImg.jpg"
}
