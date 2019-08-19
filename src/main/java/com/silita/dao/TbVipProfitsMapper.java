package com.silita.dao;

import com.silita.model.TbVipProfits;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface TbVipProfitsMapper extends MyMapper<TbVipProfits> {
    /**
     * 新增用户会员明细
     * @param param
     */
    void insertVipProfits(Map<String,Object> param);

    /**
     * 会员明细：
     * 1、赠送;2、注册;3、活动
     * @param param
     * @return
     */
    List<Map<String,Object>> queryVipProfitsSingle(Map<String,Object> param);

    List<Map<String,Object>> queryVipProfitsInviter(Map<String,Object> param);
}