package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Getter
@Setter
public class SysUserInfo extends Pagination {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 账号名(注册时登记)
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 性别(1:男；2:女；3:其他)
     */
    private Integer sex;

    /**
     * 电话号码
     */
    private String phoneNo;

    /**
     * 注册渠道（渠道编码由app，pc端定义）
     */
    private String channel;

    /**
     * 昵称
     */
    private String nikeName;

    /**
     * 证件类型
     */
    private Integer certType;

    /**
     * 证件号码
     */
    private String certNo;

    /**
     *  邮箱
     */
    private String email;

    /**
     * 出生年月(yyyy-mm-dd)
     */
    private String birthYear;

    /**
     * 所在城市
     */
    private String inCity;

    /**
     * 所在公司名
     */
    private String inCompany;

    /**
     * 职位
     */
    private String position;

    /**
     * 用户头像链接
     */
    private String imageUrl;

    /**
     * 微信号唯一标识
     */
    private String wxOpenId;

    /**
     * QQ号唯一标识
     */
    private String qqOpenId;

    /**
     * 是否可用
     */
    private Boolean isEnable;

    /**
     * 创建时间（注册时间）
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
     * 非表字段(省份code)
     */
    private String provCode;

    /**
     * 非表字段(开始时间)
     */
    private String startDate;

    /**
     * 非表字段(结束时间)
     */
    private String endDate;

    /**
     * 非表字段(关键字)
     */
    private String keyWord;

    /**
     * 非表字段(城市code)
     */
    private String cityCode;
}