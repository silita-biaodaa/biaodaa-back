package com.silita.dao;

import com.silita.model.TbCompany;
import com.silita.utils.MyMapper;

import java.util.List;

public interface TbCompanyMapper extends MyMapper<TbCompany> {

    /**
     * 查询企业列表
     * @param company
     * @return
     */
    List<TbCompany> queryCompanyList(TbCompany company);

    /**
     * 查询个数
     * @param company
     * @return
     */
    Integer queryCompanyCount(TbCompany company);
}