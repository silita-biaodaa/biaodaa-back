package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class DicQua {
    /**
     * 主键
     */
    private String id;

    /**
     * 父结点ID
     */
    private String parentId;

    /**
     * 资质名称（标准名）
     */
    private String quaName;

    /**
     * 资质等级对应的词典类型
     */
    private String gradeType;

    /**
     * 资质编码
     */
    private String quaCode;

    /**
     * 排序编号
     */
    private Short orderNo;

    /**
     * 资质层级
     */
    private String level;

    /**
     * 业务类型（0;全部；1：公告；2：企信）
     */
    private String bizType;

    /**
     * 资质标准名称
     */
    private String benchName;

    /**
     * 过期时间
     */
    private String expired;

    /**
     * 是否叶子
     */
    private Boolean isLeaf;

    /**
     * 备用字段
     */
    private String remark;

    /**
     * 描述
     */
    private String desc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 父级名称
     */
    private String parentName;

    /**
     * 等级list
     */
    private List gradeList;
}