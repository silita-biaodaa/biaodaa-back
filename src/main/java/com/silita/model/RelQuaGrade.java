package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelQuaGrade  extends Pagination {
    /**
     * 主键
     */
    private String id;

    /**
     * 资质编码
     */
    private String quaCode;

    /**
     * 资质等级类型
     */
    private String gradeType;

    /**
     * 业务类型（0;全部；1：公告；2：企信）
     */
    private String bizType;

}