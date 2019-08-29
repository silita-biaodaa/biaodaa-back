package com.silita.service;

import com.silita.model.TbUser;

import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-09 15:19
 */
public interface IUserService {

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
     * 账号管理查询及筛选
     * @param tbUser
     * @return
     */
    Map<String,Object> getAccountList(TbUser tbUser);

}
