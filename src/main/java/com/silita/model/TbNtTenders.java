package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TbNtTenders {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 公告id
     */
    private String ntId;

    /**
     * 编辑明细编码（'td'+yyyymmddHH24MMss+随机数2位）
     */
    private String editCode;

    /**
     * 标段
     */
    private String segment;

    /**
     * 招标控制价
     */
    private Double controllSum;

    /**
     * 项目金额
     */
    private Double proSum;

    /**
     * 招标状态（1:未开标
     2:流标
     3:重新招标
     4:终止
     5:中止
     6:废标
     7:延期
     8:已开标）
     */
    private String ntTdStatus;

    /**
     * 项目类型（code）
     */
    private String proType;

    /**
     * 项目工期
     */
    private String proDuration;

    /**
     * 评标办法(code)
     */
    private String pbMode;

    /**
     * 资格审查地点
     */
    private String certAuditAddr;

    /**
     * 备案平台(编码)：对应数据词典
     */
    private String filingPfm;

    /**
     * 报名地址
     */
    private String enrollAddr;

    /**
     * 开标地址
     */
    private String openingAddr;

    /**
     * 报名截止时间
     */
    private Date enrollEndTime;

    /**
     * 投标截止时间
     */
    private Date bidEndTime;

    /**
     * 投标保证金
     */
    private String bidBonds;

    /**
     * 投标保证金截止时间
     */
    private Date bidBondsEndTime;

    /**
     * 履约保证金
     */
    private Double keepBonds;

    /**
     * 其他证明金
     */
    private Double otherBonds;

    /**
     * 资格审查时间
     */
    private Date auditTime;

    /**
     * 开标人员要求
     */
    private String openingPerson;

    /**
     * 开标文件费
     */
    private Double openingFileFee;

    /**
     * 图纸及其他费用
     */
    private Double otherFee;

    /**
     * 招标人（公司id）
     */
    private String tenderee;

    /**
     * 招标联系人
     */
    private String tenderContactPerson;

    /**
     * 招标联系方式
     */
    private String tenderContactInfo;

    /**
     * 代理联系人
     */
    private String proxyContactPerson;

    /**
     * 代理联系方式
     */
    private String proxyContactInfo;

    /**
     * 项目人员要求
     */
    private String proPerson;

    /**
     * 社保要求
     */
    private String socialSecurity;

    /**
     * 业绩要求
     */
    private String achievement;

    /**
     * 报名方式(1:线上报名；2：线下报名；3：其他)
     */
    private String enrollMethod;

    /**
     * 计划竣工时间
     */
    private Date completionTime;

    /**
     * 是否流标
     */
    private Boolean isFlow;

    /**
     * 资金来源
     */
    private String fundsProvid;

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
     * 业务 业务类别(01:施工
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
     * 业务 公告url
     */
    private String url;

    /**
     * 业务 标题
     */
    private String title;

    /**
     * 业务 公示时间
     */
    private String pubDate;

    /**
     * 业务 市编码
     */
    private String cityCode;

    /**
     * 业务 县编码
     */
    private String countyCode;

    /**
     * 标段变更的字段
     */
    private String fieldName;

    /**
     * 标段变更后的值
     */
    private String fieldValue;

    /**
     * 省份来源
     */
    private String source;

    /**
     * 拼接表名称
     */
    private String tableName;

    /**
     * 公告状态
     */
    private String  ntStatus;

    /**
     * 项目地区（前端要...）
     */
    private List<Map<String, Object>> countys;

    /**
     * 评标办法name（前端要...）
     */
    private String pbModeName;

    /**
     * 城市name（前端要...）
     */
    private String cityCodeName;

    /**
     * 县区name（前端要...）
     */
    private String countyCodeName;

    /**
     * 项目类型（name）
     */
    private String proTypeName;

    /**
     * 业务类别(name)
     */
    private String binessTypeName;

    /**
     * 备案平台(name)
     */
    private String filingPfmName;

    /**
     * 招标状态(name)
     */
    private String ntTdStatusName;

    private List<TbNtRegexGroup> tbNtRegexGroups;

    /**
     * 资质关系字符串
     */
    private String qualRelationStr;
}