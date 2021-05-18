package com.demo.util;

import java.util.UUID;


public class CommonUtil {

    /**
     * 生成“唯一辨识的32位字符串”工具类
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
