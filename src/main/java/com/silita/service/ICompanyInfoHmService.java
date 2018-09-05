package com.silita.service;

import com.silita.model.TbCompanyInfoHm;

import java.util.List;
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

    /**
     * 保存统一信用代码
     * @param companyInfoHm
     * @return
     */
    Map<String,Object> saveCreditCode(TbCompanyInfoHm companyInfoHm,String username);

    /**
     * 删除统一信用代码
     * @param companyInfoHm
     */
    void delCreditCode(TbCompanyInfoHm companyInfoHm);

    /**
     * 保存企业名称
     *
     * @param companyInfoHm
     * @return
     */
    Map<String, Object> saveComName(TbCompanyInfoHm companyInfoHm, String username);

    /**
     * 获取企业变更
     *
     * @param param
     * @return
     */
    List<TbCompanyInfoHm> getComNameList(Map<String, Object> param);
}
