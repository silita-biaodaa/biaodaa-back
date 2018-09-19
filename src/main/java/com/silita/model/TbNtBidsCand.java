package com.silita.model;

import java.util.Date;

/**
 * 不要删get、set方法
 */
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


    public String getPkid() {
        return pkid;
    }
    public void setPkid(String pkid) {
        this.pkid = pkid;
    }
    public String getNtId() {
        return ntId;
    }
    public void setNtId(String ntId) {
        this.ntId = ntId;
    }
    public String getNtBidsId() {
        return ntBidsId;
    }
    public void setNtBidsId(String ntBidsId) {
        this.ntBidsId = ntBidsId;
    }
    public String getfCandidate() {
        return fCandidate;
    }
    public void setfCandidate(String fCandidate) {
        this.fCandidate = fCandidate;
    }
    public Double getfQuote() {
        return fQuote;
    }
    public void setfQuote(Double fQuote) {
        this.fQuote = fQuote;
    }
    public String getfProLeader() {
        return fProLeader;
    }
    public void setfProLeader(String fProLeader) {
        this.fProLeader = fProLeader;
    }
    public String getfTechLeader() {
        return fTechLeader;
    }
    public void setfTechLeader(String fTechLeader) {
        this.fTechLeader = fTechLeader;
    }
    public String getfBuilder() {
        return fBuilder;
    }
    public void setfBuilder(String fBuilder) {
        this.fBuilder = fBuilder;
    }
    public String getfSafety() {
        return fSafety;
    }
    public void setfSafety(String fSafety) {
        this.fSafety = fSafety;
    }
    public String getfQuality() {
        return fQuality;
    }
    public void setfQuality(String fQuality) {
        this.fQuality = fQuality;
    }
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public String getCreateBy() {
        return createBy;
    }
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public Date getUpdated() {
        return updated;
    }
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    public String getUpdateBy() {
        return updateBy;
    }
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
}