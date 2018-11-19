package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TbFinService extends Pagination {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 用户id（已注册用户）
     */
    private String userId;

    /**
     * 项目名称
     */
    private String proName;

    /**
     * 项目开标时间
     */
    private String proOpenTime;

    /**
     * 项目地区
     */
    private String proRegion;

    /**
     * 借款人名称
     */
    private String borrower;

    /**
     * 借款人电话号码
     */
    private String borrowerPhone;

    /**
     * 需借款时间
     */
    private String borrowerTime;

    /**
     * 申请金额
     */
    private Double balance;

    /**
     * 借款状态(通过；未通过)
     */
    private String status;

    /**
     * 创建时间（提交时间）
     */
    private String created;

    /**
     *
     */
    private String createdTwo;

    /**
     * 创建人
     */
    private String createBy;

}