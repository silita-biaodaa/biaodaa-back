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
    private Integer snatchurlid;

    /**
     * 公告来源省份
     */
    private String source;

    /**
     * 项目名称
     */
    private String projname;

    /**
     * 公示日期
     */
    private String gsdate;

    /**
     * 项目金额
     */
    private String projsum;

    private String projdq;

    private String projxs;

    /**
     * 评标办法
     */
    private String pbmode;

    private String projtype;

    private String projtypeid;

    /**
     * 第一候选人
     */
    private String onename;

    /**
     * 候选单位UUid
     */
    private String oneuuid;

    /**
     * 报价
     */
    private String oneoffer;

    /**
     * 项目负责人
     */
    private String oneprojduty;

    private String oneprojdutyuuid;

    /**
     * 技术负责人
     */
    private String oneskillduty;

    /**
     * 施工员
     */
    private String onesgy;

    /**
     * 安全员
     */
    private String oneaqy;

    /**
     * 质量员
     */
    private String onezly;

    /**
     * 第二候选人
     */
    private String twoname;

    /**
     * 报价
     */
    private String twooffer;

    /**
     * 项目负责人
     */
    private String twoprojduty;

    /**
     * 技术负责人
     */
    private String twoskillduty;

    /**
     * 施工员
     */
    private String twosgy;

    /**
     * 安全员
     */
    private String twoaqy;

    /**
     * 质量员
     */
    private String twozly;

    /**
     * 第三候选人
     */
    private String threename;

    /**
     * 报价
     */
    private String threeoffer;

    /**
     * 项目负责人
     */
    private String threeprojduty;

    /**
     * 技术负责人
     */
    private String threeskillduty;

    /**
     * 施工员
     */
    private String threesgy;

    /**
     * 安全员
     */
    private String threeaqy;

    /**
     * 质量员
     */
    private String threezly;

    /**
     * 创建日期
     */
    private String createddate;

    /**
     * 项目工期
     */
    private String projecttimelimit;

    /**
     * 计划竣工时间
     */
    private String projectcompletiondate;

    /**
     * 标段信息
     */
    private String block;

    private Integer zhaobdid;

}