package com.silita.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TbNtText {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 公告id
     */
    private String ntId;

    /**
     * 原文内容
     */
    private String content;

    /**
     * 原文（压缩）
     */
    private String compress;

    /**
     * 来源
     */
    private String source;
}