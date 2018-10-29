package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TbCompanyAwards extends Pagination {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 企业ID
     */
    private String comId;

    /**
     * 奖项级别(1:国家级；2：省级；3：市级）
     */
    private String level;

    /**
     * 所属地区code（省）
     */
    private String provCode;

    /**
     * 所属地区code（市）
     */
    private String cityCode;

    /**
     * 奖项名称
     */
    private String awdName;

    /**
     * 年度
     */
    private String year;

    /**
     * 项目名称
     */
    private String proName;

    /**
     * 项目类型（数据词典编码）
     */
    private String proTypeCode;

    /**
     * 项目类型
     */
    private String proTypeName;

    /**
     * 发布日期（yyyy—MM—dd）
     */
    private String issueDate;

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

    //非表字段
    /**
     * 企业名称
     */
    private String comName;
}