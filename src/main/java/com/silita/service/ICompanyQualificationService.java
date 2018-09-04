package com.silita.service;

import com.silita.model.TbCompanyQualification;

import java.util.List;
import java.util.Map;

/**
 * tb_company_qualification service
 */
public interface ICompanyQualificationService {

    /**
     * 获取企业资质
     * @param companyQualification
     * @return
     */
    List getCompanyQualList(TbCompanyQualification companyQualification);

    /**
     * 添加企业资质
     * @param param
     * @param username
     * @return
     */
    Map<String,Object> addCompanyQual(Map<String,Object> param,String username);

    /**
     * 删除资质
     * @param pkid
     */
    void delCompanyQual(String  pkid);
}
