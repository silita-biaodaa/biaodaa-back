package com.silita.dao;

import com.silita.model.TbModule;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface TbModuleMapper extends MyMapper<TbModule> {
    /**
     * 查询可编辑的权限
     * @param param
     * @return
     */
    List<Map<String,Object>> queryUpdateUserModule(Map<String,Object> param);
    /**
     * 查询可编辑的权限
     * @param param
     * @return
     */
    List<Map<String,Object>> queryAddUserModule(Map<String,Object> param);

    /**
     * 获取菜单
     * @param param
     * @return
     */
    List<Map<String,Object>> queryModule(Map<String,Object> param);


    List<Map<String,Object>> queryModuleOne();






}