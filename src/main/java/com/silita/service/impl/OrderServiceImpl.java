package com.silita.service.impl;


import com.silita.dao.SysUserInfoMapper;
import com.silita.service.IOrderService;
import com.silita.service.abs.AbstractService;
import com.silita.service.mongodb.MongodbService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

@Service
public class OrderServiceImpl extends AbstractService implements IOrderService {

    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;
    @Autowired
    private MongodbService mongodbUtils;

    /**
     * 订单列表
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getOrderListMap(Map<String, Object> param) {
        String orderType = MapUtils.getString(param, "orderType");
        List<Map<String, Object>> orderList = mongodbUtils.getOrderList(param);
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
            Map<String, Object> totalMaps = new HashMap<>();
            for (Map<String, Object> map : orderList) {
                String stdCode = MapUtils.getString(map, "stdCode");
                Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
                if (orderStatus != null && orderStatus == 9 && !stdCode.equals("report_vip") && !stdCode.equals("report_com")) {
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
                            newList.add(map);
                        }

                    } else {
                        totalMaps.put(MapUtils.getString(map, "userId"), MapUtils.getString(map, "userId"));
                        newList.add(map);
                    }
                }
            }
            List<Map<String, Object>> newOrderList = new ArrayList<>();
            if (newList != null && newList.size() > 0) {
                for (Map<String, Object> map : newList) {
                    if (MapUtils.getInteger(map, "count") > 1) {
                        String phoneNo1 = MapUtils.getString(map, "phoneNo");

                        if (StringUtil.isNotEmpty(phoneNo1)) {
                            if(!"会员用户".equals(map.get("orderType")) && !"普通用户".equals(map.get("orderType"))){
                                newOrderList.add(map);
                            }

                        }
                    }
                }
            }

            Integer pageNo = MapUtils.getInteger(param, "pageNo");
            Integer pageSize = MapUtils.getInteger(param, "pageSize");
            return getPagingResultMap(newOrderList, pageNo, pageSize);
        }
        List<Map<String, Object>> newListMaps = new ArrayList<>();
        for (Map<String, Object> map : orderList) {
            String phoneNo1 = MapUtils.getString(map, "phoneNo");
            if (StringUtil.isNotEmpty(phoneNo1)) {
                newListMaps.add(map);
            }
        }
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        return getPagingResultMap(newListMaps, pageNo, pageSize);


    }




}
