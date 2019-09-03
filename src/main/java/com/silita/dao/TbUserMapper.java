package com.silita.dao;

import com.silita.model.TbUser;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface TbUserMapper extends MyMapper<TbUser> {

    TbUser login(TbUser tbUser);

    /**
     * 用户解锁或锁定
     * @param param
     */
    void updateLock(Map<String,Object> param);

    /**
     * 修改密码
     * @param param
     */
    void updatePassword(Map<String,Object> param);

    /**
     * 重置密码
     * @param param
     */
    void updateResetPassword(Map<String,Object> param);

    /**
     * 编辑管理员
     * @param param
     */
    void updateUser(Map<String,Object> param);

    /**
     * 查询手机号码是否存在
     * @param param
     * @return
     */
    Integer querySingleUserPhone(Map<String,Object> param);

    /**
     * 查询原密码是否正确
     * @param param
     * @return
     */
    Integer querySingleUserPassword(Map<String,Object> param);

    /**
     * 账号管理查询及筛选
     * @param tbUser
     * @return
     */
    List<Map<String,Object>> queryAccountList(TbUser tbUser);

    /**
     * 账号管理查询及筛选 统计
     * @param tbUser
     * @return
     */
    Integer queryAccountListCount(TbUser tbUser);

    /**
     * 添加管理员
     * @param param
     */
    void insertAdministrator(Map<String,Object> param);

    /**
     * 获取最大uid
     * @return
     */
    Integer queryMaxUId();

    /**
     * 获取管理员手机号
     * @param param
     * @return
     */
    String queryAdministratorPhone(Map<String,Object> param);

}
