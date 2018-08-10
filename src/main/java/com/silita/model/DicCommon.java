package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DicCommon  extends Pagination {
    /**
     * 主键
     */
    private String id;

    /**
     * 父结点id
     */
    private String parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 词典类型
     */
    private String type;

    /**
     * 排序编号
     */
    private Short orderNo;

    /**
     * 是否叶子
     */
    private Boolean isLeaf;

    /**
     * 详细描述
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