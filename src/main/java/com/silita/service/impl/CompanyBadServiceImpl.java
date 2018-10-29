package com.silita.service.impl;

import com.silita.dao.TbCompanyBadMapper;
import com.silita.model.TbCompanyBad;
import com.silita.service.ICompanyBadService;
import com.silita.service.abs.AbstractService;
import org.apache.commons.collections.MapUtils;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * tb_company_bad ServiceImpl
 */
@Service
public class CompanyBadServiceImpl extends AbstractService implements ICompanyBadService {

    @Autowired
    TbCompanyBadMapper tbCompanyBadMapper;

    @Override
    public Map<String, Object> getCompanyBadList(Map<String, Object> param) {
        TbCompanyBad companyBad = new TbCompanyBad();
        companyBad.setCurrentPage(MapUtils.getInteger(param, "currentPage"));
        companyBad.setPageSize(MapUtils.getInteger(param, "pageSize"));
        companyBad.setComName(MapUtils.getString(param, "comName"));
        companyBad.setProName(MapUtils.getString(param, "proName"));
        companyBad.setBadInfo(MapUtils.getString(param, "badInfo"));
        companyBad.setIssueOrg(MapUtils.getString(param, "issueOrg"));
        companyBad.setProperty(MapUtils.getString(param, "property"));
        companyBad.setIssueDate(MapUtils.getString(param, "issueDate"));
        companyBad.setExpired(MapUtils.getString(param, "expired"));
        Integer total = tbCompanyBadMapper.queryCompanyBadCount(companyBad);
        if (total <= 0) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("list", tbCompanyBadMapper.queryCompanyBadList(companyBad));
        result.put("total", tbCompanyBadMapper.queryCompanyBadCount(companyBad));
        return super.handlePageCount(result, companyBad);
    }

    @Override
    public void batchDelCompanyBad(Map<String, Object> param) {
        String pkids = MapUtils.getString(param, "pkids");
        String[] pkidList = pkids.split("\\|");
        for (String pkid : pkidList) {
            tbCompanyBadMapper.deleteCompanyBad(pkid);
        }
    }
}
