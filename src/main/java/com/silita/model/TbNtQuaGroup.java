package com.silita.model;

public class TbNtQuaGroup {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 关系分组ID
     */
    private String groupId;

    /**
     * 资质ID
     */
    private String quaId;

    /**
     * 资质等级id
     */
    private String quaGradeId;

    /**
     * 组别内资质的和/或关系
     */
    private String relType;

    /**
     * 是否头部
     */
    private Boolean isHead;

    /**
     * 排序编号
     */
    private String px;

    public String getPkid() {
        return pkid;
    }
    public void setPkid(String pkid) {
        this.pkid = pkid;
    }
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getQuaId() {
        return quaId;
    }
    public void setQuaId(String quaId) {
        this.quaId = quaId;
    }
    public String getQuaGradeId() {
        return quaGradeId;
    }
    public void setQuaGradeId(String quaGradeId) {
        this.quaGradeId = quaGradeId;
    }
    public String getRelType() {
        return relType;
    }
    public void setRelType(String relType) {
        this.relType = relType;
    }
    public Boolean getHead() {
        return isHead;
    }
    public void setHead(Boolean head) {
        isHead = head;
    }
    public String getPx() {
        return px;
    }
    public void setPx(String px) {
        this.px = px;
    }
}