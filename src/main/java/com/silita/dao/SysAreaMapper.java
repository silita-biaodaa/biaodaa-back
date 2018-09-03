package com.silita.dao;

import java.util.List;
import java.util.Map;

public interface SysAreaMapper {

    /**
     * 根据area_parent_id获取地区数据
     * @param areaParentId
     * @return
     */
    List<Map<String, Object>> listSysAreaByParentId(String areaParentId);

    /**
     * 查询名称根据地区code
     * @param code
     * @return
     */
    String queryAreaName(String code);
}