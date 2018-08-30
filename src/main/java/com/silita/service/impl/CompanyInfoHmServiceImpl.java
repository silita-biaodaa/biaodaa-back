package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.TbCompanyInfoHmMapper;
import com.silita.dao.TbCompanyMapper;
import com.silita.model.TbCompanyInfoHm;
import com.silita.service.ICompanyInfoHmService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * tb_company_info_hm serviceimpl
 */
@Service
public class CompanyInfoHmServiceImpl extends AbstractService implements ICompanyInfoHmService {

    @Autowired
    TbCompanyInfoHmMapper companyInfoHmMapper;
    @Autowired
    TbCompanyMapper companyMapper;

    @Override
    public Map<String, Object> getCompanyInfoList(TbCompanyInfoHm companyInfoHm) {
        Map<String,Object> param = new HashMap<>();
        param.put("list",companyInfoHmMapper.queryCompanyInfoHmList(companyInfoHm));
        param.put("total",companyInfoHmMapper.queryCompanyCount(companyInfoHm));
        return super.handlePageCount(param,companyInfoHm);
    }

    @Override
    public Map<String, Object> saveCompanyInfo(TbCompanyInfoHm companyInfoHm,String username) {
        Map<String,Object> resultMap;
        if(null != companyInfoHm.getPkid()){
            resultMap = this.checkCompany(companyInfoHm,"update");
            if(MapUtils.isNotEmpty(resultMap)){
                return resultMap;
            }
            companyInfoHm.setUpdateBy(username);
            companyInfoHm.setUpdated(new Date());
            companyInfoHmMapper.updateCompanyInfo(companyInfoHm);
            resultMap = new HashMap<>();
            resultMap.put("code",Constant.CODE_SUCCESS);
            resultMap.put("msg",Constant.MSG_SUCCESS);
            return resultMap;
        }
        resultMap = this.checkCompany(companyInfoHm,"save");
        if(MapUtils.isNotEmpty(resultMap)){
            return resultMap;
        }
        companyInfoHm.setCreateBy(username);
        companyInfoHm.setCreated(new Date());
        companyInfoHm.setDataStatus(Constant.DATA_STATUS_0);
        companyInfoHm.setPkid(DataHandlingUtil.getUUID());
        companyInfoHmMapper.insertCompanyInfo(companyInfoHm);
        resultMap = new HashMap<>();
        resultMap.put("code",Constant.CODE_SUCCESS);
        resultMap.put("msg",Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public void delCompanyInfo(String pkid) {
        companyInfoHmMapper.deleteCompanyInfo(pkid);
    }

    /**
     * 校验企业名称
     * @param companyInfoHm
     * @param operate
     * @return
     */
    private Map<String,Object> checkCompany(TbCompanyInfoHm companyInfoHm,String operate){
        Map<String,Object> resultMap = null;
        TbCompanyInfoHm companyInfo = new TbCompanyInfoHm();
        Integer count = 0;
        Integer comCount = 0;
        companyInfo.setComName(companyInfoHm.getComName());
        if("update".equals(operate)){
            companyInfo.setPkid(companyInfoHm.getPkid());
        }
        count = companyInfoHmMapper.queryCompanyCount(companyInfo);
        comCount = companyMapper.queryComCount(companyInfo);
        if(count > 0 || comCount > 0){
            resultMap = new HashMap<>();
            resultMap.put("code",Constant.CODE_WARN_400);
            resultMap.put("msg","企业名称已存在!");
            return resultMap;
        }
        companyInfo.setComName(null);
        companyInfo.setCreditCode(companyInfoHm.getCreditCode());
        count = companyInfoHmMapper.queryCompanyCount(companyInfo);
        comCount = companyMapper.queryComCount(companyInfo);
        if(count > 0 || comCount > 0){
            resultMap = new HashMap<>();
            resultMap.put("code",Constant.CODE_WARN_400);
            resultMap.put("msg","信用代码已存在!");
            return resultMap;
        }
        return null;
    }
}
