package com.silita.service.impl;


import com.silita.dao.TbCompanyAwardsMapper;
import com.silita.model.TbCompanyAwards;
import com.silita.service.ICompanyAwardsService;
import com.silita.service.abs.AbstractService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * tb_company_awards ServiceImpl
 */
@Service
public class CompanyAwardsServiceImpl extends AbstractService implements ICompanyAwardsService {

    @Autowired
    TbCompanyAwardsMapper tbCompanyAwardsMapper;

    @Override
    public Map<String, Object> getCompanyAwardsList(Map<String, Object> param) {
        TbCompanyAwards companyAwards = new TbCompanyAwards();
        companyAwards.setCurrentPage(MapUtils.getInteger(param,"currentPage"));
        companyAwards.setPageSize(MapUtils.getInteger(param,"pageSize"));
        companyAwards.setComName(MapUtils.getString(param,"comName"));
        companyAwards.setLevel(MapUtils.getString(param,"level"));
        companyAwards.setProTypeCode(MapUtils.getString(param,"proTypeCode"));
        companyAwards.setCityCode(MapUtils.getString(param,"cityCode"));
        companyAwards.setProvCode(MapUtils.getString(param,"provCode"));
        companyAwards.setAwdName(MapUtils.getString(param,"awdName"));
        companyAwards.setYear(MapUtils.getString(param,"year"));
        companyAwards.setProTypeName(MapUtils.getString(param,"proTypeName"));
        companyAwards.setProName(MapUtils.getString(param,"proName"));
        Integer total = tbCompanyAwardsMapper.queryCompanyAwardsCount(companyAwards);
        if (total <= 0){
            return null;
        }
        Map<String,Object> result = new HashMap<>();
        result.put("list",tbCompanyAwardsMapper.queryCompanyAwardsList(companyAwards));
        result.put("total",total);
        return super.handlePageCount(result,companyAwards);
    }

    @Override
    public void batchDelCompanyAwards(Map<String, Object> param) {
        String pkids = MapUtils.getString(param, "pkids");
        String[] pkidList = pkids.split("\\|");
        for (String pkid : pkidList) {
            tbCompanyAwardsMapper.deleteCompanyAwards(pkid);
        }
    }
}
