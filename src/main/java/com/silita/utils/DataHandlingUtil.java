package com.silita.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-10 16:16
 */
public class DataHandlingUtil {

    /**
     * 返回省份code
     * @return
     */
    public static Map<String, String> getpProvinceCode() {
        Map map = new HashMap<String, String>(40);
        map.put("广西壮族自治区", "guangx");
        map.put("江西省", "jiangx");
        map.put("贵州省", "guiz");
        map.put("吉林省", "jil");
        map.put("河北省", "hebei");
        map.put("四川省", "sichuan");
        map.put("天津市", "tianj");
        map.put("甘肃省", "gans");
        map.put("黑龙江省", "heilj");
        map.put("青海省", "qingh");
        map.put("西藏自治区", "xizang");
        map.put("安徽省", "anh");
        map.put("北京市", "beij");
        map.put("福建省", "fuj");
        map.put("浙江省", "zhej");
        map.put("河南省", "henan");
        map.put("江苏省", "jiangs");
        map.put("内蒙古自治区", "neimg");
        map.put("宁夏回族自治区", "ningx");
        map.put("山东省", "shand");
        map.put("山西省", "sanx");
        map.put("海南省", "hain");
        map.put("上海市", "shangh");
        map.put("广东省", "guangd");
        map.put("新疆维吾尔自治区", "xinjiang");
        map.put("云南省", "yunn");
        map.put("陕西省", "shanxi");
        map.put("湖北省", "hubei");
        map.put("湖南省", "hunan");
        map.put("辽宁省", "liaon");
        map.put("重庆市", "chongq");
        return map;
    }

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
            case "湖南省":
                tabCode = "hunan";
                break;
            case "辽宁省":
                tabCode = "liaon";
                break;
            case "重庆市":
                tabCode = "chongq";
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
