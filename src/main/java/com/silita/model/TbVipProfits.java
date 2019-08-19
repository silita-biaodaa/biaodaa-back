package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
public class TbVipProfits extends Pagination {
    /**
     * 主键
     */
    @Id
    @Column(name = "v_profits_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String vProfitsId;

    /**
     * 收益标准编码
     */
    @Column(name = "settings_code")
    private String settingsCode;

    /**
     *  权利增加天数
     */
    @Column(name = "increase_days")
    private Integer increaseDays;

    /**
     * 会员id
     */
    @Column(name = "v_id")
    private String vId;

    /**
     * 推荐人邀请码
     */
    @Column(name = "inviter_code")
    private String inviterCode;

    /**
     * 会员过期日期
     */
    @Column(name = "his_expired_date")
    private Date hisExpiredDate;

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