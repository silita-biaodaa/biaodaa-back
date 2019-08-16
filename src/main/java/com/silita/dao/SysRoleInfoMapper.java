package com.silita.dao;

import com.silita.model.SysRoleInfo;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface SysRoleInfoMapper extends MyMapper<SysRoleInfo> {
    /**
     * 用户类型
     * @return
     */
    List<Map<String,Object>> queryIsVip();
}