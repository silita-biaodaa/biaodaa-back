package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
public class TbLoginInfo extends Pagination {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String pkid;

    /**
     * 登录用户名
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 登录手机号
     */
    @Column(name = "login_tel")
    private String loginTel;

    /**
     * 登录时间
     */
    @Column(name = "login_time")
    private Date loginTime;

    /**
     * 创建时间
     */
    @Column(name = "created_date")
    private Date createdDate;

    /**
     * 修改时间
     */
    @Column(name = "updated_date")
    private Date updatedDate;


}