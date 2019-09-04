package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
public class TbModule {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 父id
     */
    private Integer pid;

    /**
     * 名称
     */
    private String name;

    /**
     * url
     */
    private String url;

    private String icon;

    /**
     * 排序
     */
    private Integer sort;

}