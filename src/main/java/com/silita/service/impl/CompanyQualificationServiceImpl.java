package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.DicCommonMapper;
import com.silita.dao.DicQuaMapper;
import com.silita.dao.TbCompanyQualificationHmMapper;
import com.silita.dao.TbCompanyQualificationMapper;
import com.silita.model.DicCommon;
import com.silita.model.DicQua;
import com.silita.model.TbCompanyQualification;
import com.silita.model.TbCompanyQualificationHm;
import com.silita.service.ICompanyQualificationService;
import com.silita.utils.DataHandlingUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * tb_company_qualification serviceimpl
 */
@Service
public class CompanyQualificationServiceImpl implements ICompanyQualificationService {

    @Autowired
    TbCompanyQualificationMapper tbCompanyQualificationMapper;
    @Autowired
    TbCompanyQualificationHmMapper tbCompanyQualificationHmMapper;
    @Autowired
    DicCommonMapper dicCommonMapper;
    @Autowired
    DicQuaMapper dicQuaMapper;

    @Override
    public List getCompanyQualList(TbCompanyQualification companyQualification) {
        List list = new ArrayList();
        list = tbCompanyQualificationMapper.queryCompanyQual(companyQualification);
        TbCompanyQualificationHm qualHm = new TbCompanyQualificationHm();
        qualHm.setComId(companyQualification.getComId());
        List<TbCompanyQualificationHm> companyQualificationHmList = tbCompanyQualificationHmMapper.queryCompanyQualHm(qualHm);
        DicQua dicQua;
        DicCommon common;
        if (null != companyQualificationHmList && companyQualificationHmList.size() > 0) {
            for (TbCompanyQualificationHm hm : companyQualificationHmList) {
                dicQua = null;
                common = null;
                dicQua = dicQuaMapper.queryQualDetail(hm.getQuaCode());
                hm.setQualType(dicQuaMapper.queryQualDetailById(dicQua.getParentId()).getQuaName());
                common = dicCommonMapper.queryDicComm(hm.getRange().split("/")[1]);
                hm.setQualName(dicQua.getQuaName()+common.getName());
                hm.setChannel(Constant.SOURCE_LAB);
            }
            list.addAll(companyQualificationHmList);
        }
        return list;
    }

    @Override
    public Map<String, Object> addCompanyQual(Map<String, Object> param, String username) {
        Map<String,Object> resultMap = new HashMap<>();
        //判断资质名称是否存在
        String quaCode = MapUtils.getString(param,"quaCode");
        DicQua qua = dicQuaMapper.queryQualDetail(quaCode);
        Integer count = tbCompanyQualificationHmMapper.queryCompanyQualCountByQual(param);
        TbCompanyQualification qualification = new TbCompanyQualification();
        qualification.setComId(MapUtils.getString(param,"comId"));
        qualification.setQualName(qua.getQuaName());
        Integer quaCount = tbCompanyQualificationMapper.queryCompanyQualCount(qualification);
        if(count > 0 || quaCount > 0){
            resultMap.put("code",Constant.CODE_WARN_400);
            resultMap.put("msg",Constant.MSG_WARN_400);
            return resultMap;
        }
        TbCompanyQualificationHm companyQualificationHm = new TbCompanyQualificationHm();
        companyQualificationHm.setPkid(DataHandlingUtil.getUUID());
        companyQualificationHm.setComId(MapUtils.getString(param,"comId"));
        companyQualificationHm.setCertDate(MapUtils.getString(param,"certDate"));
        companyQualificationHm.setCertNo(MapUtils.getString(param,"certNo"));
        companyQualificationHm.setCertOrg(MapUtils.getString(param,"certOrg"));
        companyQualificationHm.setQuaCode(quaCode);
        companyQualificationHm.setRange(quaCode+"/"+MapUtils.getString(param,"gradeCode"));
        companyQualificationHm.setValidDate(MapUtils.getString(param,"validDate"));
        companyQualificationHm.setCreateBy(username);
        companyQualificationHm.setCreated(new Date());
        tbCompanyQualificationHmMapper.insertCompanyQual(companyQualificationHm);
        resultMap.put("code",Constant.CODE_SUCCESS);
        resultMap.put("msg",Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public void delCompanyQual(String pkid) {
        tbCompanyQualificationHmMapper.deleteCompanyQual(pkid);
    }
}
