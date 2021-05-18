package com.demo.util;

import java.util.Random;


public class RandomUtils {

    /**
     * 产生6位随机数(000000-999999)
     *
     * @return 6位随机数
     */
    public static String getSixRandom() {
        Random random = new Random();
        String sixRandom = random.nextInt(1000000) + "";
        int randLength = sixRandom.length();
        if (randLength < 6) {
            for (int i = 1; i <= 6 - randLength; i++) {
                sixRandom = "0" + sixRandom;
            }
        }
        return sixRandom;
    }

    //生成随机数字和字母,
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static String getFourRandom() {
        Random random = new Random();
        String sixRandom = random.nextInt(10000) + "";
        int randLength = sixRandom.length();
        if (randLength < 4) {
            for (int i = 1; i <= 4 - randLength; i++) {
                sixRandom = "0" + sixRandom;
            }
        }
        return sixRandom;
    }

    public static void main(String[] args) {
        String random = RandomUtils.getFourRandom();
        System.out.println(random);
    }

}
