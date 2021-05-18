package com.demo.util;

import java.util.regex.Pattern;

/**
 * @Author :lrj
 * @ClassName :PatternUtil
 * @Description :
 * @date :2021/4/29 15:02
 */
public class PatternUtil {


    public static void main(String[] args) {
        String pattern = "\\d*(((?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){5}\\d)\\d*)";
        String name = "1234567";
        System.out.println(panduanZZBDS(name, pattern));
    }

    public static boolean panduanZZBDS(String name, String pattern) {
        return Pattern.compile(pattern).matcher(name).matches();
    }


}
