package com.silita.service;

import java.util.List;
import java.util.Map;

public interface ModuleService {
    /**
     * 获取管理员可编辑的权限
     * @param param
     * @return
     */
    List<Map<String,Object>> getUpdateUserModule(Map<String,Object> param);
    /**
     * 获取管理员可添加的权限
     * @param
     * @return
     */
    List<Map<String,Object>> queryAddUserModule();

    /**
     * 获取菜单
     * @param param
     * @return
     */
    List<Map<String,Object>> getModule(Map<String,Object> param);

}
