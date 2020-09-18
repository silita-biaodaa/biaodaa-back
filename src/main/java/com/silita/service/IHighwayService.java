package com.silita.service;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * 公路服务
 *
 * @author Antoneo
 * @create 2020-09-09 9:30
 **/
public interface IHighwayService {
    Map<String,Object> list(Map<String, Object> param);

    Map<String,Object> show(String pkid, String type);

    Map<String,Object> reset(String pkid, String type);

    Map<String,Object> release(String pkid, String type);

    Map<String,Object> update(String pkid, String type, String mileageMan, String tunnelLen, String bridgeLen, String bridgeSpan, String bridgeWidth, ServletRequest request);

    Map<String,Object> count();

    Map<String,Object> provinces();

    Map<String,Object> parsePerson();
}
