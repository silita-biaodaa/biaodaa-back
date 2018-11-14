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
     * 业务类别(01:施工
     02:监理
     03:设计
     04:勘察
     05:采购
     06:其他
     07:PPP
     08:设计施工
     09:EPC
     10:检测
     11:施工采购
     12:造价咨询
     13:招标代理）
     */
    private String binessType;

    /**
     * 公告类目（大类）：1：招标；2：中标）
     */
    private String ntCategory;

    /**
     * 公告类型（01:一般公告
     02:磋商公告
     03:补充公告
     04:答疑公告
     05:流标公告
     06:澄清公告
     07:延期公告
     08:变更/更正/更改公告
     09:废标公告
     10:终止公告
     11:修改公告
     12:招标控制价
     13:资审结果
     14:资格预审
     15:入围公告
     16:暂停公告
     17:合同公告
     18:结果公告
     19:成交公告）
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
     * 业务 公示日期
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
     * 公告状态（0：新建；1：已编辑；2：已审核；4：审核未通过；5:已处理（不经过审核））
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

    /**
     * 非表字段:(公告内容)
     */
    private String content;
}