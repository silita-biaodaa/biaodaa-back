package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DicAlias  extends Pagination {
    /**
     * 主键
     */
    private String id;

    /**
     * 别名
     */
    private String name;

    /**
     * 别名编码
     */
    private String code;

    /**
     * 标准名称编码
     */
    private String stdCode;

    /**
     * 标准名称类型（1：资质 2：公共词典）
     */
    private String stdType;

    /**
     * 描述
     */
    private String desc;

    /**
     * 备用字段
     */
    private String remark;

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

}