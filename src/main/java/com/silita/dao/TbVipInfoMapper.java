package com.silita.dao;

import com.silita.model.TbVipInfo;
import com.silita.utils.MyMapper;

import java.util.Map;

public interface TbVipInfoMapper extends MyMapper<TbVipInfo> {
    /**
     * 新增会员用户信息
     * @param param
     */
    void insertVipInfo(Map<String,Object> param);

    /**
     * 编辑用户会员信息
     * @param param
     */
    void updateVipIfo(Map<String,Object> param);

    /**
     * 获取该用户会员信息是否为空
     * @param param
     * @return
     */
    Map<String,Object> queryVipInfoUserCount(Map<String,Object> param);

    /**
     * 获取单个用户会员信息
     * @param param
     * @return
     */
    String queryVipInfoSingle(Map<String,Object> param);

    /**
     * 获取过期时间
     * @param param
     * @return
     */
    String queryDate(Map<String,Object> param);
}