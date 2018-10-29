package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.SysAreaMapper;
import com.silita.dao.TbCompanySecurityCertMapper;
import com.silita.model.TbCompanySecurityCert;
import com.silita.service.ICompanySecurityCertService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.dateUtils.MyDateUtils;
import org.apache.commons.collections.MapUtils;
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
public class CompanySecurityCertServiceImpl extends AbstractService implements ICompanySecurityCertService {

    @Autowired
    TbCompanySecurityCertMapper tbCompanySecurityCertMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;

    @Override
    public Map<String, Object> getCompanySecurity(TbCompanySecurityCert companySecurityCert) {
        Map<String, Object> resultMap = new HashMap<>();
        List<TbCompanySecurityCert> list = tbCompanySecurityCertMapper.queryCompanySecurityList(companySecurityCert);
        if (null != list && list.size() > 0) {
            for (TbCompanySecurityCert companySecurity : list) {
                if (null != companySecurity.getCertCityCode()) {
                    companySecurity.setCertCity(sysAreaMapper.queryAreaName(companySecurity.getCertCityCode()));
                }
                if (null != companySecurity.getCertProvCode()) {
                    companySecurity.setCertProv(sysAreaMapper.queryAreaName(companySecurity.getCertProvCode()));
                }
                if (Constant.SOURCE_PRO.equals(companySecurity.getCertOrigin())) {
                    resultMap.put("pro", companySecurity);
                    resultMap.put("lab", new TbCompanySecurityCert());
                    continue;
                }
                resultMap.put("lab", companySecurity);
                resultMap.put("pro", new TbCompanySecurityCert());
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> addCertNo(TbCompanySecurityCert companySecurityCert, String username) {
        Map<String, Object> resultMap = new HashMap<>();
        //判断是否存在
        Integer count = tbCompanySecurityCertMapper.queryCertNoCount(companySecurityCert.getCertNo());
        if (count > 0) {
            resultMap.put("code", Constant.CODE_WARN_400);
            resultMap.put("msg", Constant.MSG_WARN_400);
            return resultMap;
        }
        companySecurityCert.setCertOrigin(Constant.SOURCE_LAB);
        TbCompanySecurityCert cert = tbCompanySecurityCertMapper.queryCompanySecurityDetail(companySecurityCert);
        if (null != cert) {
            tbCompanySecurityCertMapper.deleteCompanySecurity(cert.getPkid());
        }
        companySecurityCert.setPkid(DataHandlingUtil.getUUID());
        companySecurityCert.setCreateBy(username);
        companySecurityCert.setCreated(new Date());
        companySecurityCert.setExpired(MyDateUtils.strToDate(companySecurityCert.getExpiredStr(), null));
        tbCompanySecurityCertMapper.insertCompanySecurity(companySecurityCert);
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public void delCompanySeu(String pkid) {
        tbCompanySecurityCertMapper.deleteCompanySecurity(pkid);
    }

    @Override
    public Map<String, Object> addSecurity(TbCompanySecurityCert companySecurityCert, String username) {
        Map<String, Object> resultMap = new HashMap<>();
        companySecurityCert.setCertOrigin(Constant.SOURCE_LAB);
        TbCompanySecurityCert cert = tbCompanySecurityCertMapper.queryCompanySecurityDetail(companySecurityCert);
        if (null != cert) {
            tbCompanySecurityCertMapper.deleteCompanySecurity(cert.getPkid());
        }
        companySecurityCert.setPkid(DataHandlingUtil.getUUID());
        companySecurityCert.setCreateBy(username);
        companySecurityCert.setCreated(new Date());
        companySecurityCert.setExpired(MyDateUtils.strToDate(companySecurityCert.getExpiredStr(), null));
        tbCompanySecurityCertMapper.insertCompanySecurity(companySecurityCert);
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> listCompanySecurity(Map<String, Object> param) {
        TbCompanySecurityCert cert = new TbCompanySecurityCert();
        cert.setCertProvCode(MapUtils.getString(param, "certProvCode"));
        cert.setExpiredStr(MapUtils.getString(param, "expired"));
        cert.setCertNo(MapUtils.getString(param, "certNo"));
        cert.setCertCityCode(MapUtils.getString(param, "certCityCode"));
        cert.setCertLevel(MapUtils.getString(param, "certLevel"));
        cert.setCertResult(MapUtils.getString(param, "certResult"));
        cert.setComName(MapUtils.getString(param, "comName"));
        cert.setCurrentPage(MapUtils.getInteger(param, "currentPage"));
        cert.setPageSize(MapUtils.getInteger(param, "pageSize"));
        cert.setIssueDate(MapUtils.getString(param,"issueDate"));
        cert.setTabType(MapUtils.getString(param, "tabType"));
        Map<String, Object> result = new HashMap<>();
        Integer total = tbCompanySecurityCertMapper.queryCompanyCertCount(cert);
        if (total <= 0) {
            return null;
        }
        result.put("list", tbCompanySecurityCertMapper.queryCompanyCertList(cert));
        result.put("total", total);
        return super.handlePageCount(result, cert);
    }

    @Override
    public void delCompanySecurity(Map<String, Object> param) {
        String pkids = MapUtils.getString(param, "pkids");
        String[] pkidList = pkids.split("\\|");
        for (String pkid : pkidList) {
            tbCompanySecurityCertMapper.deleteCompanySecurity(pkid);
        }
    }
}
