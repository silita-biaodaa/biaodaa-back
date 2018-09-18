package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    //项目名称
    //公示日期
    //项目地区
    //项目县区
    //项目类型
    //招标类型
    //评标办法
    //资质要求
}