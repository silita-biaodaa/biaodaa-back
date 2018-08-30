package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SysArea {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 地区别名（简称）
     */
    private String areaShortName;

    /**
     * 地区编码（可用拼音简写）
     */
    private String areaCode;

    /**
     * 地区级别(1：省、自治区、直辖市；2：地级市、地区、自治州、盟；3-市辖区、县级市、县）
     */
    private Integer areaLevel;

    /**
     * 地区父id
     */
    private String areaParentId;

    /**
     * 电话区号
     */
    private String phoneCode;

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