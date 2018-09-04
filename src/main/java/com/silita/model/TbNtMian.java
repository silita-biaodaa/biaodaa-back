package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TbNtMian extends Pagination{
    /**
     * 主键
     */
    private String pkid;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告url
     */
    private String url;

    /**
     * 总标段数
     */
    private Integer segCount;

    /**
     * 省编码
     */
    private String proviceCode;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 县编码
     */
    private String countyCode;

    /**
     * 业务类别(1:建筑工程；2：政府采购；)
     */
    private String binessType;

    /**
     * 公告类目（大类）：1：招标；2：中标
     */
    private String ntCategory;

    /**
     * 公告类型（小类）
     */
    private String ntType;

    /**
     * 年份（分区字段）
     */
    private String year;

    /**
     * 公示日期
     */
    private String pubDate;

    /**
     * 结束时间
     */
    private String pubEndDate;

    /**
     * 公告源站点
     */
    private String srcSite;

    /**
     * 数据来源（区分省，冗余字段）
     */
    private String source;

    /**
     * 是否可用(0:不可用；1：可用)
     */
    private String isEnable;

    /**
     * 公告状态（0：新建；1：已编辑；2：已审核）
     */
    private String ntStatus;

    /**
     * 公告序号（备用）
     */
    private Integer px;

    /**
     * 爬取id
     */
    private String snatchId;

    /**
     * 爬取批次（yyyyMMddHHmmss）
     */
    private String snatchBatch;

    /**
     * 爬取时间
     */
    private Date snatchTime;

    /**
     * 解析记录id
     */
    private String analysisId;

    /**
     * 解析时间
     */
    private Date analysisTime;

    /**
     * 新增 招标控制价
     */
    private Double controllSum;

    /**
     * 转移 项目金额
     */
    private Double proSum;

    /**
     * 转移 项目工期
     */
    private String proDuration;

    /**
     * 转移 评标办法(code)
     */
    private String pbMode;

    /**
     * 创建时间
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
     * 拼接表名称
     */
    private String tableName;
}