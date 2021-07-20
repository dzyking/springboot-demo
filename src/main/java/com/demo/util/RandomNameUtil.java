package com.demo.util;


import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class RandomNameUtil {
    public static void main(String[] args) throws IOException {
        for (int i = 50; i-- > 0; ) {
            String name = firstName() + secondName();
            System.out.println(name);
        }
    }

    /**
     * 获取随机姓名
     */
    public static String getRandomName() {
        String name = "";
        try {
            name = firstName() + secondName();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("生成随机姓名出错{}", e.getMessage());
        }
        return name;
    }

    /*
     * 随机返回a和b其中一个数
     */
    public static int randomAB(int a, int b) {
        return (int) ((Math.random() * Math.abs(a - b)) + Math.min(a, b));
    }

    /**
     * 生成姓氏
     *
     * @throws IOException
     */
    private static String firstName() throws IOException {
        List<String> fistNames = loadBaiJiaXing("/name/姓");
        return fistNames.get(randomAB(0, fistNames.size()));
    }

    /**
     * 读取姓氏文件，获取姓氏
     *
     * @return
     * @throws IOException
     */
    private static List<String> loadBaiJiaXing(String path) throws IOException {
        //使用类加载器来加载文件
        InputStream in = RandomNameUtil.class.getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        //文件读取
        String line;
        //结果集合
        List<String> result = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            line = line.trim();
            //使用空白字符分割
            String[] names = line.split("\\s+");
            result.addAll(Arrays.asList(names));
        }
        return result;
    }

    /**
     * @return
     * @throws IOException
     * @生成名字
     */
    private static String secondName() throws IOException {
        List<String> names = loadNames("/name/名.txt");
        return names.get(randomAB(0, names.size()));
    }

    /**
     * 读取百家姓文件，获取名字
     *
     * @return
     * @throws IOException
     */
    private static List<String> loadNames(String path) throws IOException {
        InputStream in = RandomNameUtil.class.getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        //文件读取
        String line;
        //结果集合
        List<String> result = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            line = line.trim();
            //使用空白字符分割
            String[] names = line.split("\\s+");
            result.addAll(Arrays.asList(names));
        }
        return result;
    }

}

