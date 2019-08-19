package com.silita.service;

import java.util.List;
import java.util.Map;

/**
 * 会员明细
 */

public interface ITbVipProfitsService {
    /**
     * 获取会员明细
     * @param param
     * @return
     */
   List<Map<String,Object>> getVipProfitsSingle(Map<String,Object> param);
}
