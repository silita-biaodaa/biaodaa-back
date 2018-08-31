package com.silita.utils;

import com.silita.model.TbNtMian;
import com.silita.model.TbNtTenders;

import java.util.*;

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
     * 返回开标人员
     * @return
     */
    public static List<String> getBidOpeningPersonnel() {
        List result = new ArrayList<String>(10);
        result.add("法定代表人");
        result.add("项目负责人");
        result.add("授权委托人");
        result.add("法定代表人和项目负责人");
        result.add("法定代表人和授权委托人");
        result.add("项目负责人和授权委托人");
        result.add("法定代表人或项目负责人");
        result.add("法定代表人或授权委托人");
        result.add("项目负责人或授权委托人");
        return result;
    }

    /**
     * 返回项目类型
     * @return
     */
    public static List<String> getProjectType() {
        List result = new ArrayList<String>(10);
        result.add("建筑工程");
        result.add("市政公用工程");
        result.add("水利水电工程");
        result.add("公路工程");
        return result;
    }

    /**
     * 返回招标类型
     * @return
     */
    public static List<String> getBiddingType() {
        List result = new ArrayList<String>(10);
        result.add("施工");
        result.add("设计");
        result.add("监理");
        result.add("设计");
        result.add("勘察");
        return result;
    }

    /**
     * 返回平台备案要求
     * @return
     */
    public static List<String> getFilingRequirements() {
        List result = new ArrayList<String>(10);
        result.add("无");
        result.add("长沙公共资源交易电子服务平台");
        result.add("湖南省水利建设市场信用信息平台");
        result.add("全国水利建设市场信用信息平台");
        result.add("湘潭市公共资源交易中心");
        result.add("湘西州公共资源交易网");
        result.add("湖南省交通运输厅公路建设市场信用信息管理系统");
        result.add("全国公路建设市场信用信息管理系统");
        return result;
    }

    /**
     * 返回招标状态
     * @return
     */
    public static List<String> getBiddingStatus() {
        List result = new ArrayList<String>(10);
        result.add("流标");
        result.add("暂停");
        result.add("中止");
        result.add("终止");
        result.add("废标");
        result.add("延期");
        return result;
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
        }
        return tableName;
    }

    /**
     * 公告状态
     * @return
     */
    public static Map<String, Object> getBulletinStatus() {
        Map map = new HashMap<String, String>(10);
        map.put("all", "");
        map.put("edited", "1");
        map.put("unedited", "0");
        map.put("audited", "2");
        map.put("unaudited", "3");
        map.put("notAudited", "4");
        return map;
    }

}
