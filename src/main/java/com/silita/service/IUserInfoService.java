package com.silita.service;

import com.silita.model.SysUserInfo;

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
}
