package com.silita.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
//字段为null时不输出
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbNtRegexGroup {
    /**
     * 主键
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
     * 分组编码
     */
    private String groupCode;

    /**
     * 资质组之间的表达式关系
     */
    private String groupRegex;

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
     * 资质类型ID（业务）
     */
    private String quaCateId;

    /**
     * 资质ID（业务）
     */
    private String quaId;

    /**
     * 资质等级ID（业务）
     */
    private String quaGradeId;

    /**
     * 三级联动id数组（业务）
     */
    private List<String> qualIds;

    /**
     * 小组之间关联关系（业务）
     */
    private String relType;

    /**
     * 资质小组
     */
    private List<TbNtQuaGroup> tbNtQuaGroups;
}