package com.silita.service;

import java.util.Map;

public interface IOrderService {
    /**
     * 订单列表
     * @param param
     * @return
     */
    Map<String,Object> getOrderListMap(Map<String,Object> param);



}
