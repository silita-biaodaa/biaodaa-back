package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class OrderInfo extends Pagination {
    private String userID;
    private String orderNo;
    private String channelNo;
    private int orderType;
    private String stdCode;
    private int vipDays;
    private int fee;
    private String deviceInfo;
    private String tradeType;
    private String prepayId;
    private int orderStatus;
    private int isDelete;
    private Date createTime;
    private Object wxpayParam;
    private String _class;
    private Date updateTime;






}
