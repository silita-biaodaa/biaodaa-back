package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TbCompanyHighwayGrade {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 关联企业ID
     */
    private String comId;

    /**
     * 评定年度
     */
    private Integer assessYear;

    /**
     * 评定等级
     */
    private String assessLevel;

    /**
     * 评定省份code
     */
    private String assessProvCode;

    /**
     * 数据来源(1:程序；2：人工)
     */
    private String assessOrigin;

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