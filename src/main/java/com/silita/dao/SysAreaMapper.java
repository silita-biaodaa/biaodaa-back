package com.silita.dao;

import com.silita.model.SysArea;

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

    /**
     * 添加地区
     * @param area
     * @return
     */
    Integer insertArea(SysArea area);

    /**
     * 根据地区code查询pkid
     * @param areaCode
     * @return
     */
    String getPkIdByAreaCode(String areaCode);

    /**
     * 查询详情根据名称
     * @param areaName
     * @return
     */
    SysArea queryAreaByName(String areaName);

    /**
     * 根据name\parentId获取pkid
     * @param area
     * @return
     */
    String getPkIdByAreaNameAndParentId(SysArea area);

    /**
     * 根据area_parent_id获取地区name\code
     * @param areaParentId
     * @return
     */
    List<Map<String, Object>> listCodeAndNameByParentId(String areaParentId);
}