package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TbCompany extends Pagination {
    /**
     * 企业id
     */
    private String comId;

    /**
     * 本条数据的MD5值
     */
    private String md5;

    /**
     * 企业名称首字母
     */
    private String comNamePy;

    /**
     * 企业名称
     */
    private String comName;

    /**
     * 统一社会信用代码
     */
    private String creditCode;

    /**
     * 组织机构代码
     */
    private String orgCode;

    /**
     * 工商营业执照
     */
    private String businessNum;

    /**
     * 注册地址
     */
    private String regisAddress;

    /**
     * 企业营业地址
     */
    private String comAddress;

    /**
     * 法人
     */
    private String legalPerson;

    /**
     * 经济类型
     */
    private String economicType;

    /**
     * 注册资本
     */
    private String regisCapital;

    /**
     * 技术负责人
     */
    private String skillLeader;

    /**
     * url
     */
    private String url;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;

    /**
     * 排序
     */
    private Integer px;

    /**
     * 市
     */
    private String city;

    /**
     * 资质范围
     */
    private String range;

    /**
     * 公司logo
     */
    private String logo;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 修改时间
     */
    private String updateDate;
}