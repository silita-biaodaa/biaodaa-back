package com.silita.service.impl;


import com.silita.common.MongodbCommon;
import com.silita.dao.SysUserInfoMapper;
import com.silita.service.IOrderService;
import com.silita.service.abs.AbstractService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends AbstractService implements IOrderService {

    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;


    @Override
    public Map<String, Object> getOrderListMap(Map<String, Object> param) {
        String phoneNo = MapUtils.getString(param, "phoneNo");
        String orderType = MapUtils.getString(param, "orderType");
        String orderStart = MapUtils.getString(param, "orderStart");
        String orderEnd = MapUtils.getString(param, "orderEnd");


        List<Map<String, Object>> orderList = MongodbCommon.getOrderList(param);
        param.put("orderList", orderList);
        List<Map<String, Object>> list = sysUserInfoMapper.queryPhone(param);
        Map<String, Object> maps = new HashMap<>();
        for (Map<String, Object> map : list) {
            maps.put(MapUtils.getString(map, "pkid"), MapUtils.getString(map, "phoneNo"));
        }
        for (Map<String, Object> map : orderList) {
            String phone = MapUtils.getString(maps, MapUtils.getString(map, "userId"));
            if (StringUtil.isNotEmpty(phone)) {
                map.put("phoneNo", phone);
            }
        }
        List<Map<String, Object>> newList = new ArrayList<>();

        if ((StringUtil.isNotEmpty(orderType) && orderType.equals("续费会员"))) {
            Map<String,Object> totalMaps = new HashMap<>();
            for (Map<String, Object> map : orderList) {
                Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
                String stdCode = MapUtils.getString(map, "stdCode");
                if(orderStatus != null && orderStatus == 9 && (stdCode.equals("month") || stdCode.equals("quarter") ||
                        stdCode.equals("hlafYear") || stdCode.equals("year"))){
                   Map<String, Object> newMap = new HashMap<>();
                   if (newList != null && newList.size() > 0) {
                       String userId = MapUtils.getString(totalMaps, MapUtils.getString(map, "userId"));
                       if (StringUtil.isNotEmpty(userId)) {
                           for (Map<String, Object> map2 : newList) {
                               String userId1 = MapUtils.getString(map2, "userId");
                               String userId2 = MapUtils.getString(map, "userId");
                               if (StringUtil.isNotEmpty(userId1) && userId1.equals(userId2)) {
                                   Integer count = MapUtils.getInteger(map2, "count");
                                   count = count + 1;
                                   map2.put("count", count);
                               }
                           }

                       } else {
                           totalMaps.put(MapUtils.getString(map, "userId"), MapUtils.getString(map, "userId"));
                           getOrder(map,newMap,newList);
                       }

                   } else {
                       totalMaps.put(MapUtils.getString(map, "userId"), MapUtils.getString(map, "userId"));
                       getOrder(map,newMap,newList);
                   }
               }
            }
            List<Map<String, Object>> newOrderList = new ArrayList<>();
            if (newList != null && newList.size() > 0) {
                for (Map<String, Object> map : newList) {
                    Map<String, Object> newMaps = new HashMap<>();
                    if (MapUtils.getInteger(map, "count") > 1) {
                        String phoneNo1 = MapUtils.getString(map, "phoneNo");
                        if (StringUtil.isEmpty(phoneNo1)) {
                            phoneNo1 = "";
                        }
                        if (StringUtil.isNotEmpty(phoneNo1)) {
                            getOrder(map,newMaps,newOrderList);
                        }
                    }
                }
            }

            Integer pageNo = MapUtils.getInteger(param, "pageNo");
            Integer pageSize = MapUtils.getInteger(param, "pageSize");
            return getPagingResultMap(newOrderList, pageNo, pageSize);
        } else if (StringUtil.isNotEmpty(phoneNo) && StringUtil.isEmpty(orderType) && StringUtil.isEmpty(orderStart) && StringUtil.isEmpty(orderEnd)) {
            List<Map<String,Object>> newListMaps = new ArrayList<>();
            for (Map<String, Object> map : orderList) {
                Map<String,Object> newMap = new HashMap<>();
                String phoneNo1 = MapUtils.getString(map, "phoneNo");
                if(StringUtil.isNotEmpty(phoneNo1)){
                    getOrder(map,newMap,newListMaps);
                }
            }
            Integer pageNo = MapUtils.getInteger(param, "pageNo");
            Integer pageSize = MapUtils.getInteger(param, "pageSize");
            return getPagingResultMap(newListMaps, pageNo, pageSize);
        }
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        return getPagingResultMap(orderList, pageNo, pageSize);

    }


    public void getOrder(Map<String,Object> map,Map<String,Object> newMap,List<Map<String,Object>> newList){
        newMap.put("count", MapUtils.getString(map, "count"));
        String userIds = MapUtils.getString(map, "userId");
        if (StringUtil.isNotEmpty(userIds)) {
            newMap.put("userId", MapUtils.getString(map, "userId"));
        }
        String phone = MapUtils.getString(map, "phoneNo");
        if (StringUtil.isNotEmpty(phone)) {
            newMap.put("phoneNo", phone);
        }
        String orderNo = MapUtils.getString(map, "orderNo");
        if (StringUtil.isNotEmpty(orderNo)) {
            newMap.put("orderNo", MapUtils.getString(map, "orderNo"));
        }
        String orderType1 = MapUtils.getString(map, "orderType");
        if (StringUtil.isNotEmpty(orderType1)) {
            newMap.put("orderType", MapUtils.getString(map, "orderType"));
        }
        String orderStatus = MapUtils.getString(map, "orderStatus");
        if (StringUtil.isNotEmpty(orderStatus)) {
            newMap.put("orderStatus", MapUtils.getString(map, "orderStatus"));
        }
        String tradeType1 = MapUtils.getString(map, "tradeType");
        if (StringUtil.isNotEmpty(tradeType1)) {
            newMap.put("tradeType", MapUtils.getString(map, "tradeType"));
        }
        String money = MapUtils.getString(map, "money");
        if (StringUtil.isNotEmpty(money)) {
            newMap.put("money", MapUtils.getString(map, "money"));
        }
        String truePay = MapUtils.getString(map, "truePay");
        if (StringUtil.isNotEmpty(truePay)) {
            newMap.put("truePay", MapUtils.getString(map, "truePay"));
        }
        newMap.put("createTime", MapUtils.getString(map, "createTime"));
        newList.add(newMap);
    }

}
