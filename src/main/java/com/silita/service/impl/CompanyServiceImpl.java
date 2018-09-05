package com.silita.service.impl;

import com.silita.dao.TbCompanyMapper;
import com.silita.model.TbCompany;
import com.silita.service.ICompanyService;
import com.silita.service.abs.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tb_company serviceimpl
 */
@Service
public class CompanyServiceImpl extends AbstractService implements ICompanyService {

    @Autowired
    TbCompanyMapper tbCompanyMapper;

    @Override
    public Map<String, Object> getCompanyList(TbCompany company) {
        Map<String, Object> param = new HashMap<>();
        if (null != company && null != company.getCity()) {
            if (company.getCity().contains("市")) {
                company.setCity(company.getCity().replace("市", ""));
            }
        }
        param.put("list", tbCompanyMapper.queryCompanyList(company));
        param.put("total", tbCompanyMapper.queryCompanyCount(company));
        return super.handlePageCount(param, company);
    }

    @Override
    public TbCompany getCreditCode(Map<String, Object> param) {
        TbCompany company = new TbCompany();
        List<TbCompany> companyList = tbCompanyMapper.queryCompanyDetailHm(param);
        if(null != companyList && companyList.size() > 0){
            company = companyList.get(0);
        }
        return company;
    }
}
