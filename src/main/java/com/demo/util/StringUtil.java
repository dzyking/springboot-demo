package com.demo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 字符串工具类
 *
 * @author ruoyi
 */
public class StringUtil extends org.apache.commons.lang3.StringUtils {
    /**
     * 空字符串
     */
    private static final String NULLSTR = "";

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';

    /**
     * 获取参数不为空值
     *
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     *                * @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * 判断一个对象数组是否非空
     *
     * @param objects 要判断的对象数组
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * * 判断一个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * * 判断一个字符串是否为非空串
     *
     * @param str String
     * @return true：非空串 false：空串
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * * 判断一个对象是否是数组类型（Java基本型别的数组）
     *
     * @param object 对象
     * @return true：是数组 false：不是数组
     */
    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return NULLSTR;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULLSTR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return NULLSTR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 驼峰式命名法
     * 例如：user_name->userName
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    public static String encode(String str) {
        String encode = null;
        try {
            encode = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }

    /**
     * 获取UUID，去掉`-`的
     *
     * @return {String}
     */
    public static String generateStr() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 要求外部订单号必须唯一。
     *
     * @return {String}
     */
    public static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            key += random.nextInt(10);
        }
        return key;
    }

    /**
     * @param
     * @author: lrj
     * @Description:随机生成头像文件名称
     * @Date: 2021/5/8
     * @return: java.lang.String
     */
    public static String getPhotoName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            key += random.nextInt(10);
        }
        return key;
    }

    /**
     * @param @param  length
     * @param @return
     * @return String
     * @Title: getCharAndNumr
     * @Description: 生成随机数字和字母组合
     * @author lrj
     * @Date 2020年11月11日下午5:43:59
     */
    public static String getRandomChar(int length) {
        Random random = new Random();
        StringBuffer valSb = new StringBuffer();
        String charStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int charLength = charStr.length();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charLength);
            valSb.append(charStr.charAt(index));
        }
        return valSb.toString();
    }

    /**
     * 字符串格式化
     * <p>
     * use: format("my name is {0}, and i like {1}!", "L.cm", "java")
     * <p>
     * int long use {0,number,#}
     *
     * @param s
     * @param args
     * @return {String}转换后的字符串
     */
    public static String format(String s, Object... args) {
        return MessageFormat.format(s, args);
    }

    /**
     * 替换某个字符
     *
     * @param str
     * @param regex
     * @param args
     * @return {String}
     */
    public static String replace(String str, String regex, String... args) {
        int length = args.length;
        for (int i = 0; i < length; i++) {
            str = str.replaceFirst(regex, args[i]);
        }
        return str;
    }

    /**
     * 清理字符串，清理出某些不可见字符
     *
     * @param txt
     * @return {String}
     */
    public static String cleanChars(String txt) {
        return txt.replaceAll("[ 　	`·•�\\f\\t\\v]", "");
    }

    /**
     * 随机字符串
     */
    private static final String INT_TEMP = "0123456789";
    private static final String STR_TEMP = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String ALL_TEMP = INT_TEMP + STR_TEMP;

    private static final Random RANDOM = new Random();

    /**
     * 生成的随机数类型
     */
    public enum RandomType {
        /**
         * 整数
         */
        INT,
        /**
         * 字符串
         */
        STRING,
        /**
         * 所有类型
         */
        ALL
    }

    /**
     * 随机数生成
     *
     * @param count
     * @return {String}
     */
    public static String random(int count, RandomType randomType) {
        if (count == 0) {
            return "";
        }
        if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }
        char[] buffer = new char[count];
        for (int i = 0; i < count; i++) {
            if (randomType.equals(RandomType.INT)) {
                buffer[i] = INT_TEMP.charAt(RANDOM.nextInt(INT_TEMP.length()));
            } else if (randomType.equals(RandomType.STRING)) {
                buffer[i] = STR_TEMP.charAt(RANDOM.nextInt(STR_TEMP.length()));
            } else {
                buffer[i] = ALL_TEMP.charAt(RANDOM.nextInt(ALL_TEMP.length()));
            }
        }
        return new String(buffer);
    }

    /**
     * @param @param  text
     * @param @param  sub
     * @param @return
     * @return int
     * @Title: count
     * @Description: sub字符串在text字符串中出现的次数
     * @author lrj
     * @Date 2021年1月20日上午10:07:33
     */
    public static int count(String text, String sub) {
        if (isEmpty(text) || isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int start = 0;
        while ((start = text.indexOf(sub, start)) >= 0) {
            start += sub.length();
            count++;
        }
        return count;
    }

    /**
     * @param @param  mailRemark
     * @param @return
     * @return String
     * @Title: getRemark
     * @Description: 截取患者备注
     * @author lrj
     * @Date 2021年1月20日上午10:14:10
     */
    public static String getRemark(String mailRemark) {
        if (isEmpty(mailRemark)) {
            return mailRemark;
        }
        // 截取多次介绍中多余的部分
        mailRemark = deleteRepeatStrMiddle(mailRemark, "介绍");
        mailRemark = deleteRepeatStrMiddle(mailRemark, "症");
        return mailRemark;
    }

    /**
     * @param @param  text
     * @param @param  sub
     * @param @return
     * @return String
     * @Title: deleteRepeatStrMiddle
     * @Description: 删除重复字符串中间的字符串
     * @author lrj
     * @Date 2021年1月20日上午11:22:53
     */
    public static String deleteRepeatStrMiddle(String text, String sub) {
        if (isEmpty(text) || isEmpty(sub)) {
            return text;
        }
        // 截取开始位置
        int start = text.indexOf(sub) + sub.length();
        int end = text.lastIndexOf(sub) + sub.length();
        // 创建对象
        StringBuffer sb1 = new StringBuffer();
        sb1.append(text);
        sb1.delete(start, end);
        text = sb1.toString();
        return text;
    }

    public static void main(String[] args) {
        System.out.println(getRemark("123介绍456介绍介绍789介绍789重症"));
    }
}