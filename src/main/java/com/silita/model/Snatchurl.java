package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Snatchurl extends Pagination {
    private Integer id;

    /**
     * 数据库按年份分区用
     */
    private Integer range;

    /**
     * 抓取的地址
     */
    private String url;

    /**
     * 当时所属的页码
     */
    private Integer urlPage;

    /**
     * 抓取时间
     */
    private Date snatchDateTime;

    /**
     * 所属的抓取计划ID
     */
    private Integer snatchPlanId;

    /**
     * 类型:0招标信息，招标预告，招标变更1，中标结果2
     */
    private Integer type;

    /**
     * 类型:默认0;1补充公告,2答疑公告
     */
    private Integer otherType;

    /**
     * 类型:0施工;1设计,2监理,3采购
     */
    private Integer biddingType;

    /**
     * 同批次标志
     */
    private String uuid;

    /**
     * 标题
     */
    private String title;

    /**
     * 是否被抓取:0没,1已抓
     */
    private Integer status;

    /**
     * 发布时间
     */
    private String openDate;

    private String openDateEnd;

    private String keysWords;

    /**
     * 公告人工编辑0未1已
     */
    private Integer edit;

    /**
     * 标段
     */
    private String block;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 随机数
     */
    private Integer randomNum;

    /**
     * uuid
     */
    private String suuid;

    /**
     * 内容变更次数
     */
    private Integer changeNum;

    /**
     * 逻辑删除：0显示，1不显示
     */
    private Integer isShow;

    /**
     * 省code
     */
    private String province;

    /**
     * 市code
     */
    private String city;

    /**
     * 县code
     */
    private String county;

    /**
     * 公告所属地区等级
     */
    private Integer rank;

    /**
     * ridisId
     */
    private Integer redisId;

    /**
     * 公告所有网站id
     */
    private Integer websitePlanId;

    /**
     * 省份路由
     */
    private String source;
}