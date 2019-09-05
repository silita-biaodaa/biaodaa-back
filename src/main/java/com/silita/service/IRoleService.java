package com.silita.service;

import com.silita.model.TbRole;

import java.util.List;
import java.util.Map;

public interface IRoleService {
    /**
     * 添加角色及权限
     * @param param
     */
    Map<String,Object> addRole(Map<String,Object> param);

    /**
     * 修改角色及权限
     * @param param
     * @return
     */
    Map<String,Object> updateRole(Map<String,Object> param);

    /**
     * 获取角色列表
     * @param role
     * @return
     */
    Map<String,Object> getRoleList(TbRole role);

    /**
     * 获取所有角色
     * @return
     */
    List<Map<String,Object>> getRoleAll();

}
