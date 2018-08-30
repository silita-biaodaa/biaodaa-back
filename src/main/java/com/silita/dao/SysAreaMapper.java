package com.silita.dao;

import com.silita.model.SysArea;

import java.util.List;
import java.util.Map;

public interface SysAreaMapper {

    /**
     * 根据area_parent_id获取地区数据
     * @param sysArea
     * @return
     */
    List<Map<String, Object>> listSysAreaByParentId(SysArea sysArea);
}