package com.demo.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo.entity.exception.BaseErrorInfoInterface;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 返回结果实体类
 */
public class Result {

    /**
     * @throws
     * @Title: result
     * @Description: 接口返回（字符类型字段如果为null,输出为"",而非null，数值字段如果为null,输出为0,而非null，List字段如果为null,输出为[],而非null）
     * @param: @param code
     * @param: @param msg
     * @param: @return
     * @return: String
     * @author: lrj
     * @date: 2020年6月22日 下午6:16:06
     */
    public static String result(Integer code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toString();
    }

    /**
     * @throws
     * @Title: result
     * @Description: 为null的字段不显示的方法
     * @param: @param code
     * @param: @param msg
     * @param: @return
     * @return: String
     * @author: lrj
     * @date: 2020年6月22日 下午6:18:07
     */
    @JsonInclude(value = Include.NON_NULL)
    public static String resultNONull(Integer code, String msg, Object data) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        json.put("data", data);
        return json.toString();
    }

    public static String result(Integer code, String msg, Object data) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        json.put("data", data);
        return json.toString(SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero);
    }

    public static String result(Integer code, Object data) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("data", data);
        return json.toString(SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero);
    }

    /**
     * @throws
     * @Title: result
     * @Description: 返回数据
     * @param: @param code
     * @param: @param msg
     * @param: @param data
     * @param: @param pages	分页总页数
     * @param: @return
     * @return: JSONObject
     * @author: lrj
     * @date: 2020年4月28日 下午1:44:10
     */
    public static String result(Integer code, String msg, Object data, int pages) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        json.put("data", data);
        json.put("pages", pages);
        return json.toString(SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero);
    }

    /**
     * @param @param  code
     * @param @param  msg
     * @param @param  fileName
     * @param @param  url
     * @param @return
     * @return String
     * @Title: result
     * @Description: 编辑器上传图片返回
     * @author lrj
     * @Date 2020年11月13日上午9:56:42
     */
    public static String fileResult(Integer code, String msg, String fileName, String url) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        json.put("fileName", fileName);
        json.put("url", url);
        return json.toString(SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero);
    }

    /**
     * @param @param  code
     * @param @param  msg
     * @param @param  jsonStr
     * @param @param  orderNum
     * @param @return
     * @return String
     * @Title: orderNumResult
     * @Description: 调起支付时返回orderNum
     * @author lrj
     * @Date 2020年11月19日上午11:12:57
     */
    public static String orderNumResult(Integer code, String msg, String jsonStr, String orderNum) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        json.put("data", jsonStr);
        json.put("orderNum", orderNum);
        return json.toString(SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero);
    }

    /**
     * 失败
     */
    public static String error(BaseErrorInfoInterface errorInfo) {
        JSONObject json = new JSONObject();
        json.put("code", errorInfo.getResultCode());
        json.put("msg", errorInfo.getResultMsg());
        return json.toString();
    }

    /**
     * 失败
     */
    public static String error(String code, String message) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", message);
        return json.toString();
    }

    public static String success(Object data){
        JSONObject json = new JSONObject();
        json.put("code", StatusCode.OK);
        json.put("msg", "成功");
        json.put("data", data);
        return json.toString(SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero);
    }

    public static String success(){
        JSONObject json = new JSONObject();
        json.put("code", StatusCode.OK);
        json.put("msg", "操作成功");
        return json.toString();
    }
}
