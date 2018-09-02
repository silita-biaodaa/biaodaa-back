package com.silita.dao;

import com.silita.model.TbCompanySecurityCert;
import com.silita.utils.MyMapper;

import java.util.List;

public interface TbCompanySecurityCertMapper extends MyMapper<TbCompanySecurityCert> {

    /**
     * 查询安全认证信息
     * @param companySecurityCert
     * @return
     */
    List<TbCompanySecurityCert> queryCompanySecurityList(TbCompanySecurityCert companySecurityCert);

    /**
     * 添加
     * @param companySecurityCert
     * @return
     */
    Integer insertCompanySecurity(TbCompanySecurityCert companySecurityCert);

    /**
     * 修改
     * @param companySecurityCert
     * @return
     */
    Integer updateCompanySecurity(TbCompanySecurityCert companySecurityCert);

    /**
     * 删除
     * @param pkid
     * @return
     */
    Integer deleteCompanySecurity(String pkid);

    /**
     * 查询详情
     * @param companySecurityCert
     * @return
     */
    TbCompanySecurityCert queryCompanySecurityDetail(TbCompanySecurityCert companySecurityCert);

    /**
     *  查询安许证号是否已存在
     * @param certNo
     * @return
     */
    Integer queryCertNoCount(String certNo);
}