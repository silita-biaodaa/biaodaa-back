package com.silita.common;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ConstantMap {

    /**
     * 奖项级别
     */
    public static Map<String, String> AWARDSMAP = new HashMap<>();

    /**
     * 不良行为性质
     */
    public static Map<String, String> PROPERTYMAP = new HashMap<>();

    /**
     * 安全认证级别
     */
    public static Map<String, String> CERTLEVELMAP = new HashMap<>();

    /**
     * 安全认证结果
     */
    public static Map<String, String> CERTRESULTMAP = new HashMap<>();

    static {
        AWARDSMAP.put("国家级", "1");
        AWARDSMAP.put("省级", "2");
        AWARDSMAP.put("市级", "3");

        PROPERTYMAP.put("严重", "1");
        PROPERTYMAP.put("一般", "2");

        CERTLEVELMAP.put("省级", "1");
        CERTLEVELMAP.put("市级", "2");

        CERTRESULTMAP.put("优秀", "1");
        CERTRESULTMAP.put("合格", "2");
    }
}
