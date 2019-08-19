package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
public class TbVipInfo extends Pagination {
    /**
     * 主键
     */
    @Id
    @Column(name = "v_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String vId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 会员级别
     */
    @Column(name = "LEVEL")
    private Integer level;

    /**
     * 备注
     */
    private String remark;

    /**
     * 权限id集合（逗号分隔）
     */
    private String permissions;

    /**
     * 角色编码（后天管理自定义）
     */
    @Column(name = "role_code")
    private String roleCode;

    /**
     * 会员过期日期
     */
    @Column(name = "expired_date")
    private Date expiredDate;

    /**
     * 创建时间（注册时间）
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