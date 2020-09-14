package com.silita.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 公路解析编辑实体
 *
 * @author Antoneo
 * @create 2020-09-09 11:31
 **/
@Getter
@Setter
public class HighwayEditVo {
    //主键
    private String pkid;

    //项目名称
    private String projName;

    //项目标段
    private String section;

    //审核状态: 0未操作，1操作中，2已操作
    private  int  isOpt;

    //合同开始桩号
    private String sectionStart;

    //合同结束桩号
    private String sectionEnd;

    //主要工程量
    private String remark;

    //程序解析里程长度（km）
    private String mileage;

    //人工解析里程数
    private String mileageMan;

    //企业名称
    private String compName;

    //隧道长度
    private String tunnelLen;
    //桥梁长度
    private String bridgeLen;
    //桥梁跨度
    private String bridgeSpan;
    //桥面宽度
    private String bridgeWidth;

    //类型：build，design
    private String type;
}
