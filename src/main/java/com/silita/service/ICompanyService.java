package com.silita.service;

import com.silita.model.TbCompany;

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
}
