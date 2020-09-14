package com.silita.service;

import java.util.Map;

/**
 * 公路服务
 *
 * @author Antoneo
 * @create 2020-09-09 9:30
 **/
public interface IHighwayService {
    Map<String,Object> list(int pageNo, int pageSize, int sourceType, String nameKey, String province, int isOpt);

    Map<String,Object> show(String pkid, String type);

    Map<String,Object> reset(String pkid, String type);

    Map<String,Object> release(String pkid, String type);

    Map<String,Object> update(String pkid, String type,String mileageMan,String tunnelLen,String bridgeLen,String bridgeSpan,String bridgeWidth);

    Map<String,Object> count();

    Map<String,Object> provinces();
}
