package com.silita.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Snatchurlcontent {
    private Integer id;

    /**
     * 对应snatchUrl的id
     */
    private Integer snatchUrlId;

    /**
     * 抓取页面的内容
     */
    private String content;

    private String press;

    private String contentPress;

    /**
     * 省份路由
     */
    private String source;

}