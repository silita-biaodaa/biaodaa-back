package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TbNtBidsCand {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 公告pkid
     */
    private String ntId;

    /**
     * 中标编辑明细pkid
     */
    private String ntBidsId;

    /**
     * 中标候选人（统一社会编码多个用,分割）
     */
    private String fCandidate;

    /**
     * 报价
     */
    private Double fQuote;

    /**
     * 项目负责人
     */
    private String fProLeader;

    /**
     * 技术负责人
     */
    private String fTechLeader;

    /**
     * 施工员
     */
    private String fBuilder;

    /**
     * 安全员
     */
    private String fSafety;

    /**
     * 质量员
     */
    private String fQuality;

    /**
     * 中标候选人序号
     */
    private Integer number;

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
     * 数据来源（区分省，冗余字段）
     */
    private String source;

}