package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TbNtTenders {
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
     * 标段
     */
    private String segment;

    /**
     * 报名地址
     */
    private String enrollAddr;

    /**
     * 开标地址
     */
    private String openingAddr;

    /**
     * 报名截止时间
     */
    private String enrollEndTime;

    /**
     * 投标截止时间
     */
    private String bidEndTime;

    /**
     * 投标保证金
     */
    private String bidBonds;

    /**
     * 投标保证金截止时间
     */
    private String bidBondsEndTime;

    /**
     * 履约保证金
     */
    private Double keepBonds;

    /**
     * 其他证明金
     */
    private Double otherBonds;

    /**
     * 资格审查时间
     */
    private String auditTime;

    /**
     * 开标人员要求
     */
    private String openingPerson;

    /**
     * 开标文件费
     */
    private Double openingFileFee;

    /**
     * 图纸及其他费用
     */
    private Double otherFee;

    /**
     * 招标人（公司id）
     */
    private String tenderee;

    /**
     * 招标联系人
     */
    private String tenderContactPerson;

    /**
     * 招标联系方式
     */
    private String tenderContactInfo;

    /**
     * 代理联系人
     */
    private String proxyContactPerson;

    /**
     * 代理联系方式
     */
    private String proxyContactInfo;

    /**
     * 项目人员要求
     */
    private String proPerson;

    /**
     * 社保要求
     */
    private String socialSecurity;

    /**
     * 业绩要求
     */
    private String achievement;

    /**
     * 报名方式(1:线上报名；2：线下报名；3：其他)
     */
    private String enrollMethod;

    /**
     * 计划竣工时间
     */
    private String completionTime;

    /**
     * 是否流标
     */
    private Boolean isFlow;

    /**
     * 资金来源
     */
    private String fundsProvid;

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
     * 新增 资格审查地点
     */
    private String certAuditAddr;

    /**
     * 新增 备案平台(编码)
     */
    private String filingPfm;

    /**
     * 业务更新 业务类别(1:建筑工程；2：政府采购；)
     */
    private String binessType;

    /**
     * 业务更新 公告类目（大类）：1：招标；2：中标
     */
    private String ntCategory;

    /**
     * 业务更新 公告类型（小类）
     */
    private String ntType;

    /**
     * 业务更新 招标控制价
     */
    private Double controllSum;

    /**
     * 业务更新 项目金额
     */
    private Double proSum;

    /**
     * 业务更新 项目工期
     */
    private String proDuration;

    /**
     * 业务更新 评标办法(code)
     */
    private String pbMode;

    /**
     * 业务更新 公告url
     */
    private String url;

    /**
     * 业务 标题
     */
    private String title;

    /**
     * 业务 公示时间
     */
    private String pubDate;

    /**
     * 省份来源
     */
    private String source;

    /**
     * 拼接表名称
     */
    private String tableName;
}