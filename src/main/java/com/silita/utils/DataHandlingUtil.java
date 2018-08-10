package com.silita.utils;

import java.util.UUID;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-10 16:16
 */
public class DataHandlingUtil {

    /**
     * 根据省份生成代码
     * @param province
     * @return
     */
    public static String getCode(String province) {
        String tabCode = "";
        switch (province) {
            case "广西壮族自治区":
                tabCode = "guangx";
                break;
            case "江西省":
                tabCode = "jiangx";
                break;
            case "贵州省":
                tabCode = "guiz";
                break;
            case "吉林省":
                tabCode = "jil";
                break;
            case "河北省":
                tabCode = "hebei";
                break;
            case "四川省":
                tabCode = "sichuan";
                break;
            case "天津市":
                tabCode = "tianj";
                break;
            case "甘肃省":
                tabCode = "gans";
                break;
            case "黑龙江省":
                tabCode = "heilj";
                break;
            case "青海省":
                tabCode = "qingh";
                break;
            case "西藏自治区":
                tabCode = "xizang";
                break;
            case "安徽省":
                tabCode = "anh";
                break;
            case "北京市":
                tabCode = "beij";
                break;
            case "福建省":
                tabCode = "fuj";
                break;
            case "浙江省":
                tabCode = "zhej";
                break;
            case "河南省":
                tabCode = "henan";
                break;
            case "江苏省":
                tabCode = "jiangs";
                break;
            case "内蒙古自治区":
                tabCode = "neimg";
                break;
            case "宁夏回族自治区":
                tabCode = "ningx";
                break;
            case "山东省":
                tabCode = "shand";
                break;
            case "山西省":
                tabCode = "sanx";
                break;
            case "海南省":
                tabCode = "hain";
                break;
            case "上海市":
                tabCode = "shangh";
                break;
            case "广东省":
                tabCode = "guangd";
                break;
            case "新疆维吾尔自治区":
                tabCode = "xinjiang";
                break;
            case "云南省":
                tabCode = "yunn";
                break;
            case "陕西省":
                tabCode = "shanxi";
                break;
            case "湖北省":
                tabCode = "hubei";
                break;
            default:
                tabCode = null;
                break;
        }
        return tabCode;
    }

    /**
     * 生成uuid
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return uuid;
    }
}
