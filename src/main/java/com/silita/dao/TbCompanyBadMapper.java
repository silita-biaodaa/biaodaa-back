package com.silita.dao;

import com.silita.model.TbCompanyBad;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * tb_company_bad Mapper
 */
public interface TbCompanyBadMapper extends MyMapper<TbCompanyBad> {

    /**
     * 查询企业不良行为个数
     * @param companyBad
     * @return
     */
    Integer queryCompanyBadCount(TbCompanyBad companyBad);

    /**
     * 查询企业不良行为
     * @param companyBad
     * @return
     */
    List<Map<String,Object>> queryCompanyBadList(TbCompanyBad companyBad);

    /**
     * 删除
     * @param pkid
     * @return
     */
    int deleteCompanyBad(String pkid);

    /**
     * 查询个数
     * @param param
     * @return
     */
    Integer queryBadCount(Map<String,Object> param);

    /**
     * 批量添加
     * @param list
     * @return
     */
    int batchInsertCompanyBad(List<TbCompanyBad> list);
}