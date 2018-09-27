package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TbNtBids {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 公告id
     */
    private String ntId;

    /**
     * 编辑明细编码（'td'+yyyymmddHH24MMss+随机数2位）
     */
    private String editCode;

    /**
     * 招标编辑明细编码（明细关联）
     */
    private String tdEditCode;

    /**
     * 标段
     */
    private String segment;

    /**
     * 招标控制价
     */
    private Double controllSum;

    /**
     * 项目金额
     */
    private Double proSum;

    /**
     * 项目类型（code）
     */
    private String proType;

    /**
     * 项目工期
     */
    private String proDuration;

    /**
     * 评标办法(code)
     */
    private String pbMode;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updated;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 数据来源（区分省，冗余字段）
     */
    private String source;

    /**
     * 拼接表名称
     */
    private String tableName;


    /**
     * 公告标题（项目名称）
     */
    private String title;

    /**
     * 公示日期
     */
    private String pubDate;

    /**
     * 市编码（项目地区）
     */
    private String cityCode;

    /**
     * 县编码（项目县区）
     */
    private String countyCode;

    /**
     * 业务类别(01:施工
     02:监理
     03:设计
     04:勘察
     05:采购
     06:其他
     07:PPP
     08:设计施工
     09:EPC
     10:检测
     11:施工采购
     12:造价咨询
     13:招标代理）
     */
    private String binessType;

    /**
     * 公告url
     */
    private String url;

    //资质要求

    /**
     * 中标候选人
     */
    private List<TbNtBidsCand> bidsCands;

    /**
     * 项目地区（前端要...）
     */
    private List<Map<String, Object>> countys;

    /**
     * 评标办法name（前端要...）
     */
    private String pbModeName;

    /**
     * 城市name（前端要...）
     */
    private String cityCodeName;

    /**
     * 先区name（前端要...）
     */
    private String countyCodeName;

    /**
     * 变更字段
     */
    private String changeFieldName;

    /**
     * 变更值
     */
    private String changeFieldValue;

    /**
     * 项目类别name
     */
    private String proTypeName;

    /**
     * 业务类别name
     */
    private String binessTypeName;
}