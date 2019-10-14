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

    /**
     * 获取评标办法列表
     * @param param
     * @return
     */
    List<Map<String,Object>> getList(Map<String,Object> param);

    /**
     * 批量删除公告词典数据
     * @param param
     */
    void deleteDicCommonIds(Map<String,Object> param);

    /**
     * 修改评标办法名称
     * @param param
     */
    Map<String,Object> updateDicCommonId(Map<String,Object> param);



    /**
     * 获取省市
     * @return
     */
    List<Map<String,Object>> getAreas();

    /**
     * 获取评标办法
     * @param param
     * @return
     */
    List<Map<String,Object>> queryPbModes(Map<String,Object> param);



    /**
     * 获取公告类型
     * @return
     */
    List<Map<String,Object>> type();

    /**
     * 获取资质
     * @param param
     * @return
     */
    List<Map<String,Object>> queryQua(Map<String,Object> param);


}
