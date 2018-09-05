package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Setter
@Getter
public class TbCompanyInfoHm extends Pagination {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 关联企业ID
     */
    private String comId;

    /**
     * 企业名称
     */
    private String comName;

    /**
     * 数据状态(0:未抓取；1：已抓取；2：)
     */
    private String dataStatus;

    /**
     * 地区
     */
    private String comAddress;

    /**
     * 变更时间
     */
    private String changeTime;

    /**
     * 信用代码
     */
    private String creditCode;

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
     * 创建时间
     */
    private String createDate;

    /**
     * 更新时间
     */
    private String updateDate;

    /**
     * 来源
     */
    private String source;

    /**
     * 变更前名称
     */
    private String comNameEx;
}