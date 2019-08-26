package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Getter
@Setter
@Document(collection = "order_info")
public class OrderInfo extends Pagination implements java.io.Serializable{
    private String userId;
    private String orderNo;
    private String channelNo;
    private Integer orderType;
    private String stdCode;
    private Integer vipDays;
    private Integer fee;
    private String deviceInfo;
    private String tradeType;
    private String prepayId;
    @Indexed
    private Integer orderStatus;
    private Integer isDelete;
    private Date createTime;
    private String _class;
    private Date updateTime;






}
