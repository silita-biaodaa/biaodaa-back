package com.silita.dao;

import com.silita.model.TbUser;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface TbUserMapper extends MyMapper<TbUser> {
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
     * 查询单个管理员
     * @param param
     * @return
     */
    Map<String,Object> querySingleUser(Map<String,Object> param);

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
}
