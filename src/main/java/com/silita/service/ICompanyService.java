package com.silita.service;

import com.silita.model.TbCompany;

import java.util.List;
import java.util.Map;

/**
 * tb_company service
 */
public interface ICompanyService {

    /**
     * 获取企业列表
     * @param company
     * @return
     */
    Map<String,Object> getCompanyList(TbCompany company);

    /**
     * 获取统一信用代码
     *
     * @param param
     * @return
     */
    TbCompany getCreditCode(Map<String, Object> param);
}
