package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.SysAreaMapper;
import com.silita.dao.TbCompanySecurityCertMapper;
import com.silita.model.TbCompanySecurityCert;
import com.silita.service.ICompanySecurityCertService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.MyDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tb_Company_security_cert serviceimpl
 */
@Service
public class CompanySecurityCertServiceImpl implements ICompanySecurityCertService {

    @Autowired
    TbCompanySecurityCertMapper tbCompanySecurityCertMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;

    @Override
    public Map<String, Object> getCompanySecurity(TbCompanySecurityCert companySecurityCert) {
        Map<String,Object> resultMap = new HashMap<>();
        List<TbCompanySecurityCert> list = tbCompanySecurityCertMapper.queryCompanySecurityList(companySecurityCert);
        if(null != list && list.size() > 0){
            for (TbCompanySecurityCert companySecurity : list){
                if(null != companySecurity.getCertCityCode()){
                    companySecurity.setCertCity(sysAreaMapper.queryAreaName(companySecurity.getCertCityCode()));
                }
                if(null != companySecurity.getCertProvCode()){
                    companySecurity.setCertProvCode(sysAreaMapper.queryAreaName(companySecurity.getCertProvCode()));
                }
                if(Constant.SOURCE_PRO.equals(companySecurity.getCertOrigin())){
                    resultMap.put("pro",companySecurity);
                    continue;
                }
                resultMap.put("lab",companySecurity);
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> addCertNo(TbCompanySecurityCert companySecurityCert, String username) {
        Map<String,Object> resultMap = new HashMap<>();
        //判断是否存在
        Integer count = tbCompanySecurityCertMapper.queryCertNoCount(companySecurityCert.getCertNo());
        if(count > 0){
            resultMap.put("code",Constant.CODE_WARN_400);
            resultMap.put("msg",Constant.MSG_WARN_400);
            return resultMap;
        }
        companySecurityCert.setCertOrigin(Constant.SOURCE_LAB);
        TbCompanySecurityCert cert = tbCompanySecurityCertMapper.queryCompanySecurityDetail(companySecurityCert);
        if(null != cert){
            tbCompanySecurityCertMapper.deleteCompanySecurity(cert.getPkid());
        }
        companySecurityCert.setPkid(DataHandlingUtil.getUUID());
        companySecurityCert.setCreateBy(username);
        companySecurityCert.setCreated(new Date());
        companySecurityCert.setExpired(MyDateUtils.strToDate(companySecurityCert.getExpiredStr(),null));
        tbCompanySecurityCertMapper.insertCompanySecurity(companySecurityCert);
        resultMap.put("code",Constant.CODE_SUCCESS);
        resultMap.put("msg",Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public void delCompanySeu(String pkid) {
        tbCompanySecurityCertMapper.deleteCompanySecurity(pkid);
    }

    @Override
    public Map<String, Object> addSecurity(TbCompanySecurityCert companySecurityCert, String username) {
        Map<String,Object> resultMap = new HashMap<>();
        companySecurityCert.setCertOrigin(Constant.SOURCE_LAB);
        TbCompanySecurityCert cert = tbCompanySecurityCertMapper.queryCompanySecurityDetail(companySecurityCert);
        if(null != cert){
            tbCompanySecurityCertMapper.deleteCompanySecurity(cert.getPkid());
        }
        companySecurityCert.setPkid(DataHandlingUtil.getUUID());
        companySecurityCert.setCreateBy(username);
        companySecurityCert.setCreated(new Date());
        companySecurityCert.setExpired(MyDateUtils.strToDate(companySecurityCert.getExpiredStr(),null));
        tbCompanySecurityCertMapper.insertCompanySecurity(companySecurityCert);
        resultMap.put("code",Constant.CODE_SUCCESS);
        resultMap.put("msg",Constant.MSG_SUCCESS);
        return resultMap;
    }
}
