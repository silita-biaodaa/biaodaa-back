package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TbNtAssociateGp extends Pagination {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 公告id
     */
    private String ntId;

    /**
     * 关系组（多条关联的公告同一个关系组）
     */
    private String relGp;

    /**
     * 关联类型（1：招标；2：中标；3：其他）
     */
    private String relType;

    /**
     * 关系排序编号
     */
    private String px;

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
     * 公告标题
     */
    private String title;

    /**
     * 公示日期
     */
    private String pubDate;

    /**
     * 结束时间
     */
    private String pubEndDate;

    /**
     * 数据来源（区分省，冗余字段）
     */
    private String source;

    /**
     * 拼接表名称
     */
    private String tableName;
}