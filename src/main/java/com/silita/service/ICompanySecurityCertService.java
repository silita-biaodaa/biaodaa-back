package com.silita.service;

import com.silita.model.TbCompanySecurityCert;

import java.util.Map;

/**
 * tb_Company_security_cert service
 */
public interface ICompanySecurityCertService {

    /**
     * 获取安许证号
     * @param companySecurityCert
     * @return
     */
    Map<String,Object> getCompanySecurity(TbCompanySecurityCert companySecurityCert);

    /**
     * 添加安许证号
     * @param companySecurityCert
     * @param username
     */
    Map<String, Object> addCertNo(TbCompanySecurityCert companySecurityCert,String username);

    /**
     * 删除
     * @param pkid
     */
    void delCompanySeu(String pkid);

    /**
     * 添加安全认证
     * @param companySecurityCert
     * @param username
     * @return
     */
    Map<String,Object> addSecurity(TbCompanySecurityCert companySecurityCert,String username);
}