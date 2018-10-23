package com.silita.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZhongbiaoDetailOthers {
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
     * 公示日期
     */
    private String gsDate;

    /**
     * 项目金额
     */
    private String projSum;

    private String projDq;

    private String projXs;

    /**
     * 评标办法
     */
    private String pbMode;

    private String projType;

    private String projTypeId;

    /**
     * 第一候选人
     */
    private String oneName;

    /**
     * 候选单位UUid
     */
    private String oneUUid;

    /**
     * 报价
     */
    private String oneOffer;

    /**
     * 项目负责人
     */
    private String oneProjDuty;

    private String oneProjDutyUuid;

    /**
     * 技术负责人
     */
    private String oneSkillDuty;

    /**
     * 施工员
     */
    private String oneSgy;

    /**
     * 安全员
     */
    private String oneAqy;

    /**
     * 质量员
     */
    private String oneZly;

    /**
     * 第二候选人
     */
    private String twoName;

    /**
     * 报价
     */
    private String twoOffer;

    /**
     * 项目负责人
     */
    private String twoProjDuty;

    /**
     * 技术负责人
     */
    private String twoSkillDuty;

    /**
     * 施工员
     */
    private String twoSgy;

    /**
     * 安全员
     */
    private String twoAqy;

    /**
     * 质量员
     */
    private String twoZly;

    /**
     * 第三候选人
     */
    private String threeName;

    /**
     * 报价
     */
    private String threeOffer;

    /**
     * 项目负责人
     */
    private String threeProjDuty;

    /**
     * 技术负责人
     */
    private String threeSkillDuty;

    /**
     * 施工员
     */
    private String threeSgy;

    /**
     * 安全员
     */
    private String threeAqy;

    /**
     * 质量员
     */
    private String threeZly;

    /**
     * 创建日期
     */
    private String createdDate;

    /**
     * 项目工期
     */
    private String projectTimeLimit;

    /**
     * 计划竣工时间
     */
    private String projectCompletionDate;

    /**
     * 标段信息
     */
    private String block;

    private Integer zhaobdId;

}