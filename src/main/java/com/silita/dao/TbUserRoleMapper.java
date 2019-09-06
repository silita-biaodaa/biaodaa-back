package com.silita.dao;

import com.silita.model.TbUserRole;
import com.silita.utils.MyMapper;

import java.util.Map;

public interface TbUserRoleMapper extends MyMapper<TbUserRole> {
    /**
     * 删除管理员对应角色
     * @param param
     */
    void deleteUserRole(Map<String,Object> param);

    /**
     * 添加管理员对应角色
     * @param param
     */
    void insertUserRole(Map<String,Object> param);

    /**
     * 获取角色id
     * @param param
     * @return
     */
    Integer queryRid(Map<String,Object> param);

}