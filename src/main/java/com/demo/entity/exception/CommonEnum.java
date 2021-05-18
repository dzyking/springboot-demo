package com.demo.entity.exception;

public enum CommonEnum implements BaseErrorInfoInterface {
    // 数据操作错误定义
    SUCCESS("200", "请求成功!"), BODY_NOT_MATCH("400", "请求失败"), SIGNATURE_NOT_MATCH("401", "请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"), INTERNAL_SERVER_ERROR("500", "服务器内部错误!"), SERVER_BUSY("503", "服务器正忙，请稍后再试!"),
    Method_Not_Supported("400", "请求方法不支持!"), MethodArgument_Not_Valid("422", "请求参数不合法!"),
    ERROR_TRY_AGAIN_FAILED("507", "系统异常，多次重试失败！！！"), INSUFFICIENT_ACCOUNT_BALANCE("9001", "账户余额不足！"),
    HAS_WAIT_WITHDRAWAL_ORDER("9002", "存在未处理提现订单！"), INSEART_WITHDRAWAL_BAD("9003", "录入提现订单异常！"),
    USED_USERCOUPONS_FAILURE("9004", "用户下单使用优惠券失败！");

    CommonEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    /**
     * 错误码
     */
    private final String resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }

}
