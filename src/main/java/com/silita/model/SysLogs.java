package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
public class SysLogs extends Pagination {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String pkid;

    /**
     * 操作类型
     */
    @Column(name = "opt_type")
    private String optType;

    /**
     * 操作表
     */
    @Column(name = "opt_table")
    private String optTable;

    /**
     * 变更值
     */
    @Column(name = "opt_value")
    private String optValue;

    /**
     * 操作概要
     */
    @Column(name = "opt_desc")
    private String optDesc;

    /**
     * 操作人id
     */
    @Column(name = "opt_by")
    private String optBy;

    /**
     * 操作时间
     */
    @Column(name = "opt_time")
    private Date optTime;

    private String realName;


}