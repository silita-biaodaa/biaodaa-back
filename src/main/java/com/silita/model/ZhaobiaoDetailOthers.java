package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ZhaobiaoDetailOthers {
    private Integer id;

    /**
     * 公告id
     */
    private Integer snatchUrlId;

    /**
     * 公告来源省份
     */
    private String source;

    /**
     * 项目名称
     */
    private String projName;

    /**
     * 公示时间
     */
    private String gsDate;

    /**
     * 项目地区
     */
    private String projDq;

    /**
     * 项目县市
     */
    private String projXs;

    /**
     * 项目类型
     */
    private String projType;

    private String projTypeId;

    /**
     * 资质
     */
    private String zzRank;

    /**
     * 项目金额
     */
    private String projSum;

    /**
     * 评标办法
     */
    private String pbMode;

    /**
     * 报名开始时间
     */
    private String bmStartDate;

    /**
     * 报名结束时间
     */
    private String bmEndDate;

    /**
     * 报名结束时点
     */
    private String bmEndTime;

    /**
     * 报名地点
     */
    private String bmSite;

    /**
     * 投标保证金额
     */
    private String tbAssureSum;

    /**
     * 投标保证金截止时间
     */
    private String tbAssureEndDate;

    /**
     * 投标保证金截止时点
     */
    private String tbAssureEndTime;

    /**
     * 履约保证金
     */
    private String lyAssureSum;

    /**
     * 其他证明金
     */
    private String slProveSum;

    /**
     * 保证金截止时间
     */
    private String assureEndDate;

    /**
     * 保证金截止时点
     */
    private String assureEndTime;

    /**
     * 资格审查时间
     */
    private String zgCheckDate;

    /**
     * 投标截止时间
     */
    private String tbEndDate;

    /**
     * 投标截止时点
     */
    private String tbEndTime;

    /**
     * 开标人员要求
     */
    private String kbStaffAsk;

    /**
     * 开标地点
     */
    private String kbSite;

    /**
     * 开标文件费
     */
    private String fileCost;

    /**
     * 图纸及其它费
     */
    private String otherCost;

    /**
     * 招标人
     */
    private String zbName;

    /**
     * 招标联系人
     */
    private String zbContactMan;

    /**
     * 招标联系方式
     */
    private String zbContactWay;

    /**
     * 代理人
     */
    private String dlName;

    /**
     * 代理联系人
     */
    private String dlContactMan;

    /**
     * 代理联系方式
     */
    private String dlContactWay;

    /**
     * 人员要求
     */
    private String personRequest;

    /**
     * 社保要求
     */
    private String shebaoRequest;

    /**
     * 业绩要求
     */
    private String yejiRequest;

    /**
     * 创建时间
     */
    private String createdDate;

    /**
     * 报名方式
     */
    private String registrationForm;

    /**
     * 项目工期
     */
    private String projectTimeLimit;

    /**
     * 计划竣工时间
     */
    private String projectCompletionDate;

    /**
     * 补充公告次数
     */
    private String supplementaryNoticeNumber;

    /**
     * 补充公告原因
     */
    private String supplementaryNoticeReason;

    /**
     * 是否流标
     */
    private String flowStandardFlag;

    /**
     * 标段信息
     */
    private String block;

    /**
     * 资金来源
     */
    private String money;

    /**
     * 标准资质
     */
    List<SnatchUrlCert> snatchUrlCerts;

    /**
     * 资质别名
     */
    List<AllZh> allZhs;

}