package com.silita.dao;

import com.silita.model.SysUserInfo;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * sys_user_info Mapper
 */
public interface SysUserInfoMapper extends MyMapper<SysUserInfo> {

    /**
     * 查询用户统计
     *
     * @return
     */
    int queryUserCount(Map<String, Object> param);

    /**
     * 查询数量
     *
     * @param userInfo
     * @return
     */
    int queryUserTotal(SysUserInfo userInfo);

    /**
     * 锁定用户
     *
     * @param param
     * @return
     */
    int lockUser(Map<String, Object> param);

    /**
     * 查询列表
     * @param userInfo
     * @return
     */
    List<Map<String,Object>> queryUserList(SysUserInfo userInfo);
}