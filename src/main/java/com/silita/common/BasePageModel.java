package com.silita.common;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装了分页的信息
 *  */
@Getter
@Setter
public class BasePageModel implements java.io.Serializable {

    private static final long serialVersionUID = -8963316942190157192L;

    private String[] ids;
    private int page;// 当前页
    private int rows = 10;// 每页显示记录数
    private int offset = -1;//当前偏移量
    private String sortord;// 排序字段名和排序方式
    /**
     * setter 、getter
     */
}

