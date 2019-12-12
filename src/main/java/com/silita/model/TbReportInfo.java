package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TbReportInfo {
    /**
     * 主键id
     */
    private Integer pkid;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 报告标题
     */
    private String repTitle;

    /**
     * 收费标准码
     */
    private String stdCode;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 报告生成格式(PDF,Word)
     */
    private String pattern;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 报告存储路径
     */
    private String reportPath;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date updated;

    /**
     * 报告查询条件
     */
    private String repCondition;

    /**
     * 微信订单号
     */
    private String transactionId;
}