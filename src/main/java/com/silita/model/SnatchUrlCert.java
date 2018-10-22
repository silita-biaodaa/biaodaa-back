package com.silita.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnatchUrlCert {
    private Integer id;

    /**
     * 公告snatchUrlId
     */
    private Integer contId;

    /**
     * 资质要求
     */
    private String certificate;

    /**
     * 资质uuid
     */
    private String certificateUuid;

    /**
     * 安全生产许可证
     */
    private String licence;

    /**
     * 公告类型0招标；1中标
     */
    private String type;

    /**
     * 标段信息
     */
    private String block;

    /**
     * 省份来源
     */
    private String source;

    /**
     * 业务查询资质uuid
     */
    private String tempMainUuid;

    /**
     * 业务查询 资质等级
     */
    private String tempRank;

}