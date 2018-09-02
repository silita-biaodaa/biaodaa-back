package com.silita.service.impl;

import com.silita.dao.TbCompanyQualificationMapper;
import com.silita.model.TbCompanyQualification;
import com.silita.service.ICompanyQualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * tb_company_qualification serviceimpl
 */
@Service
public class CompanyQualificationServiceImpl implements ICompanyQualificationService {

    @Autowired
    TbCompanyQualificationMapper tbCompanyQualificationMapper;

    @Override
    public List<TbCompanyQualification> getCompanyQualList(TbCompanyQualification companyQualification) {
        return tbCompanyQualificationMapper.queryCompanyQual(companyQualification);
    }

    @Override
    public Map<String, Object> addCompanyQual(Map<String, Object> param, String username) {
        return null;
    }

    @Override
    public void delCompanyQual(String pkid) {
        tbCompanyQualificationMapper.deleteCompanyQual(pkid);
    }
}
