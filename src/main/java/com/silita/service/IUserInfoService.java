package com.silita.service;

import com.silita.model.SysUserInfo;

import java.util.List;
import java.util.Map;

/**
 * sys_user_info service
 */
public interface IUserInfoService {

    /**
     * 用户统计
     * @param param
     * @return
     */
    Map<String,Object> userCount(Map<String,Object> param);

    /**
     * 用户锁定
     * @param param
     * @return
     */
    void userLock(Map<String,Object> param);

    /**
     * 获取列表
     * @param userInfo
     * @return
     */
    Map<String,Object> listUserInfo(SysUserInfo userInfo);

    /**
     * 获取活跃用户统计
     * @return
     */
    Map<String,Object> getActiveUserCount();

    /**
     * 活跃用户列表
     * @param param
     * @return
     */
    Map<String,Object> getActiveUserList(Map<String,Object> param);

    /**
     * 获取用户列表
     * @param param
     * @return
     */
    Map<String,Object> getUserInfo(Map<String,Object> param);

    /**
     * 用户统计
     * @return
     */
    Map<String,Object> getUserCount();

    /**
     * 获取个人用户信息
     * @param param
     * @return
     */
    Map<String,Object> getSingleUserInfo(Map<String,Object> param);

    /**
     * 修改备注
     * @param param
     */
    void updateRemark(Map<String,Object> param);

    /**
     * 邀请人信息
     * @param param
     * @return
     */
    Map<String,Object> getInviterInfo(Map<String,Object> param);

    /**
     * 订单统计
     * @return
     */
    Map<String,Object> getOrderCount();

}
