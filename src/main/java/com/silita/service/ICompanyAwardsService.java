package com.silita.service;

import java.util.Map;

/**
 * tb_company_awards Service
 */
public interface ICompanyAwardsService {

    /**
     * 获取获奖列表
     * @param param
     * @return
     */
    Map<String,Object> getCompanyAwardsList(Map<String,Object> param);

    /**
     * 批量删除
     * @param param
     */
    void batchDelCompanyAwards(Map<String,Object> param);
}
