package com.silita.utils;

import com.silita.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
        Map<String, String> map = new HashMap(40);
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
     * 公告状态
     * @return
     */
    public static Map<String, Object> getBulletinStatus() {
        Map<String, Object> map = new HashMap(10);
        map.put("all", ""); //全部
        map.put("untreated", "0");  //未处理
        map.put("unaudited", "1");  //未审核
        map.put("Passed", "2"); //已通过
        map.put("notThrough", "4"); //未通过
        map.put("processed", "5");  //已处理
        return map;
    }

    /**
     * 生成uuid
     * @return
     */
    public synchronized static String getUUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return uuid;
    }

    /**
     * 获取时间戳
     * @return
     */
    public synchronized static String getTimeStamp() {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    /**
     * 生成随机数
     * @param length
     * @return
     */
    public static String getNumberRandom(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            val.append(String.valueOf(random.nextInt(10)));
        }
        return val.toString();
    }

    /**
     * 拼接表名称
     * @param model
     * @param source
     * @return
     */
    public static String SplicingTable(Class<?> model, String source) {
        String tableName = null;
        if(model.getName().equals(TbNtMian.class.getName())) {
            tableName = "tb_nt_mian_" + source;
        } else if(model.getName().equals(TbNtTenders.class.getName())) {
            tableName = "tb_nt_tenders_" + source;
        } else if(model.getName().equals(TbNtAssociateGp.class.getName())) {
            tableName = "tb_nt_associate_gp_" + source;
        } else if(model.getName().equals(TbNtText.class.getName())) {
            tableName = "tb_nt_text_" + source;
        } else if(model.getName().equals(TbNtBids.class.getName())) {
            tableName = "tb_nt_bids_" + source;
        }
        return tableName;
    }

}
