package com.silita.service;

import com.silita.model.TbUser;

import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-09 15:19
 */
public interface IUserService {

    Integer existssqlPhone(TbUser tbUser);

    TbUser login(TbUser tbUser);

    /**
     * 根据用户名获取用户基本信息
     * @param userName
     * @return
     */
    TbUser getUserByUserName(String userName);

    /**
     * 根据用户名获取用户角色、权限
     * @param userName
     * @return
     */
    Map<String, Object> getRolesAndPermissionsByUserName(String userName);

    /**
     * 用户锁定或解锁
     * @param param
     */
    void updateLock(Map<String,Object> param);

    /**
     * 修改密码
     * @param param
     */
    Map<String,Object> updatePassword(Map<String,Object> param);

    /**
     * 重置密码
     * @param param
     */
    void updateResetPassword(Map<String,Object> param);
    /**
     * 账号管理查询及筛选
     * @param tbUser
     * @return
     */
    Map<String,Object> getAccountList(TbUser tbUser);

    /**
     * 修改管理员信息
     * @param param
     */
    Map<String,Object> updateAdministrator(Map<String,Object> param);

    /**
     * 添加管理员
     * @param param
     */
    Map<String,Object> addAdministrator(Map<String,Object> param);

}
