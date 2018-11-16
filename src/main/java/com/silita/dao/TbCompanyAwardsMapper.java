package com.silita.dao;

import com.silita.model.TbCompanyAwards;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * tb_company_awards Mapper
 */
public interface TbCompanyAwardsMapper extends MyMapper<TbCompanyAwards> {

    /**
     * 查询企业列表个数
     * @param companyAwards
     * @return
     */
    Integer queryCompanyAwardsCount(TbCompanyAwards companyAwards);

    /**
     * 查询企业列表
     * @param companyAwards
     * @return
     */
    List<Map<String,Object>> queryCompanyAwardsList(TbCompanyAwards companyAwards);

    /**
     * 删除
     * @param pkid
     * @return
     */
    int deleteCompanyAwards(String pkid);

    /**
     * 根据条件删除删除
     * @param list
     * @return
     */
    int deleteCompanyAwardsForParam(List<Map<String,Object>> list);

    /**
     * 批量添加获奖信息
     * @param list
     * @return
     */
    void batchInsertCompanyAwrds(List<TbCompanyAwards> list);

    /**
     * 查询个数
     * @param param
     * @return
     */
    Integer queryAwardsCount(Map<String,Object> param);
}