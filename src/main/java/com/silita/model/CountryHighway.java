package com.silita.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 全国公路在建/设计
 *
 * @author Antoneo
 * @create 2020-09-07 16:34
 **/
@Getter
@Setter
public class CountryHighway {

    //主键
    private String pkid;

    //项目名称
    private String projName;

    //项目标段
    private String section;

    //所在省份
    private String province;

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

    //类型：build，design
    private String type;

}
