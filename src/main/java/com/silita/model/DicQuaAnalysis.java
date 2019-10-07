package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
public class DicQuaAnalysis extends Pagination {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 资质关系表达式id
     */
    private String relId;

    /**
     * 别名id
     */
    private String ailasId;

    /**
     * 资质别名+等级别名 = 组合别名
     */
    private String jointAilas;

    /**
     * 资质标准名称+等级
     */
    private String quaLevel;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;


    private String rank;

    private String sort;


}