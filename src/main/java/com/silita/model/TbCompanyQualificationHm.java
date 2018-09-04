package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TbCompanyQualificationHm {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 关联企业ID
     */
    private String comId;

    /**
     * 企业名称
     */
    private String comName;

    /**
     * 证书编号
     */
    private String certNo;

    /**
     * 发证机构
     */
    private String certOrg;

    /**
     * 发证日期
     */
    private String certDate;

    /**
     * 证书有效期
     */
    private String validDate;

    /**
     * 资质范围
     */
    private String range;

    /**
     * 资质编码
     */
    private String quaCode;

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
     * 发文日期
     */
    private String issueDate;

    /**
     * 资质类别
     */
    private String qualType;

    /**
     * 资质名称
     */
    private String qualName;

    /**
     * 来源
     */
    private String channel;
}