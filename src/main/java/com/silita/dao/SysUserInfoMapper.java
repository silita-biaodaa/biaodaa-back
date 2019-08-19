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

    /**
     * 获取活跃用户统计
     * @param param
     * @return
     */
    Map<String,Object> queryActiveUserCount(Map<String,Object> param);

    /**
     * 活跃用户列表
     * @param param
     * @return
     */
    List<Map<String,Object>> queryActiveUserList(Map<String,Object> param);

    /**
     * 用户列表
     * @param param
     * @return
     */
    List<Map<String,Object>> queryUserInfoList(Map<String,Object> param);

    /**
     * 获取用户统计
     * @param param
     * @return
     */
    Map<String,Object> queryUserInfoCount(Map<String,Object> param);

    /**
     * 获取用户个人信息
     * @param param
     * @return
     */
    Map<String,Object> querySingleUserInfo(Map<String,Object> param);

    /**
     * 过期
     * @return
     */
    List<Map<String,Object>> queryPast();

    /**
     * 修改备注
     * @param param
     */
    void updateRemark(Map<String,Object> param);

    /**
     * 邀请人信息
     * @param param
     */
    List<Map<String,Object>> queryInviterInfo(Map<String,Object> param);

    String queryinviterCode(Map<String,Object> param);
}