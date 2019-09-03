package com.silita.dao;

import com.silita.model.TbRoleModule;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface TbRoleModuleMapper extends MyMapper<TbRoleModule> {

    /**
     * 添加用户权限
     * @param param
     */
    void insertRoleModule(Map<String,Object> param);

    //List<Map<String,Object>>
}