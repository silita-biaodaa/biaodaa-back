package com.silita.service;

import com.silita.model.TbCompanyInfoHm;

import java.util.Map;

/**
 * tb_company_info_hm service
 */
public interface ICompanyInfoHmService {

    /**
     * 人工添加企业列表
     *
     * @param companyInfoHm
     * @return
     */
    Map<String, Object> getCompanyInfoList(TbCompanyInfoHm companyInfoHm);

    /**
     * 保存企业
     *
     * @param companyInfoHm
     * @return
     */
    Map<String, Object> saveCompanyInfo(TbCompanyInfoHm companyInfoHm, String username);

    /**
     * 删除企业
     *
     * @param pkid
     */
    void delCompanyInfo(String pkid);
}
