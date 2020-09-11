package com.silita.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 公路列表实体
 *
 * @author Antoneo
 * @create 2020-09-09 10:30
 **/
@Getter
@Setter
public class HighwayVo{
    //主键
    private String pkid;

    //项目名称
    private String projName;

    //项目标段
    private String section;

    //来源
    private String source;

    //所在省份
    private String province;

    //审核状态: 0未操作，1操作中，2已操作
    private  int  isOpt;

    //区分表，build,design
    private String type;
}
