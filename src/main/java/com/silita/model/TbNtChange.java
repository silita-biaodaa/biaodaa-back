package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TbNtChange {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 公告id
     */
    private String ntId;

    /**
     * 公告id
     */
    private String ntEditId;

    /**
     * 公告类目（大类）：1：招标；2：中标
     */
    private String ntCategory;

    /**
     * 数据来源（区分省，对应表相关表后缀）
     */
    private String source;

    /**
     * 变更前
     */
    private String fieldFrom;

    /**
     * 变更字段
     */
    private String fieldName;

    /**
     * 变更值
     */
    private String fieldValue;

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