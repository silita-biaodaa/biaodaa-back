package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TbCompanySecurityCert {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 企业ID
     */
    private String comId;

    /**
     * 安许证号
     */
    private String certNo;

    /**
     * 认证级别(1:省级；2：市级)
     */
    private String certLevel;

    /**
     * 认证结果(1:优秀；2：合格)
     */
    private String certResult;

    /**
     * 过期时间
     */
    private Date expired;

    /**
     * 过期时间(String类型)
     */
    private String expiredStr;

    /**
     * 认证地区code（省）
     */
    private String certProvCode;

    /**
     * 认证地区（省）
     */
    private String certProv;

    /**
     * 认证地区code（市）
     */
    private String certCityCode;

    /**
     * 认证地区（市）
     */
    private String certCity;

    /**
     * 数据来源(1:程序抓取；2：人工编辑)
     */
    private String certOrigin;

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
     * 获取主键
     *
     * @return pkid - 主键
     */
    public String getPkid() {
        return pkid;
    }
}