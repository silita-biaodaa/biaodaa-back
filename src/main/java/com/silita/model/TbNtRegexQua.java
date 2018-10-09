package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TbNtRegexQua {
    /**
     * pkid
     */
    private String pkid;

    /**
     * 公告编辑信息ID
     */
    private String ntEditId;

    /**
     * 公告id
     */
    private String ntId;

    /**
     * 资质关系表达式(程序算法生成）
     */
    private String quaRegex;

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