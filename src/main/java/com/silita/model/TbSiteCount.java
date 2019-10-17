package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
public class TbSiteCount {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 站点当天新增公告数量
     */
    private Integer siteTodayCount;

    /**
     * 站点归属地区
     */
    private String source;

    /**
     * 发布时间
     */
    private Date pubDate;

}