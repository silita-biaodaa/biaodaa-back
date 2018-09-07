package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SysFiles {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 业务对象id
     */
    private String bizId;

    /**
     * 对象类型(1:公告文件；2：图片；3：app附件；4长传文件路径)
     */
    private String type;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * oss对象名
     */
    private String ossObj;

    /**
     * 关系排序编号
     */
    private String orderNo;

    /**
     * 省份来源
     */
    private String source;

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
     * 文件是否失效
     */
    private String status;

}