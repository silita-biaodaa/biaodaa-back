package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TbCompanyBad {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 企业ID
     */
    private String comId;

    /**
     * 项目名称
     */
    private String proName;

    /**
     * 不良行为内容
     */
    private String badInfo;

    /**
     * 性质（1：严重；2：一般）
     */
    private String property;

    /**
     * 发布单位
     */
    private String issueOrg;

    /**
     * 发布日期（yyyy—MM—dd）
     */
    private String issueDate;

    /**
     * 有效期至（yyyy—MM—dd）
     */
    private String expired;

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

}