package com.silita.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}