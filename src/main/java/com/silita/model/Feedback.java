package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;
@Getter
@Setter
public class Feedback extends Pagination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 反馈人id
     */
    private String pid;

    /**
     * 反馈问题提交时间
     */
    private Date starttime;

    /**
     * 问题所在路径
     */
    private String path;

    /**
     * 模块
     */
    private String module;

    /**
     * 问题描述
     */
    private String problem;

    private Integer state;

    /**
     * 问题类型
     */
    private String type;

    /**
     * 终端类型
     */
    @Column(name = "loginChannel")
    private String loginchannel;

    /**
     * 终端版本号
     */
    private String version;



}