package com.silita.service;

import java.util.List;
import java.util.Map;

/**
 * common service
 */
public interface ICommonService {

    /**
     * 获取省/市
     * @return
     */
    List<Map<String,Object>> getArea();

}
