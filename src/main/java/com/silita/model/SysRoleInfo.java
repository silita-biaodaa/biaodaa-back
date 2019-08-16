package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
public class SysRoleInfo extends Pagination {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String pkid;

    /**
     * 角色名
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 角色编码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 角色描述
     */
    private String desc;

    /**
     * 权限/菜单编码（逗号分隔）
     */
    private String permissions;

    /**
     * 权限等级（数值越大，权限越高）
     */
    private Integer grade;

    /**
     * 是否可用
     */
    @Column(name = "is_enable")
    private Boolean isEnable;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 更新时间
     */
    private Date updated;

    /**
     * 更新人
     */
    @Column(name = "update_by")
    private String updateBy;


}