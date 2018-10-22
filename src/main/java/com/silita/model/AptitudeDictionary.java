package com.silita.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AptitudeDictionary {
    private Integer id;

    /**
     * 类型名称
     */
    private String aptitudeName;

    /**
     * 类型代码
     */
    private String aptitudeCode;

    /**
     * 资质名称
     */
    private String majorName;

    /**
     * 资质uuid
     */
    private String majorUuid;

    /**
     * 等级类型用：1=一二三级，2=甲乙丙级
     */
    private String zztype;

}