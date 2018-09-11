package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.SysAreaMapper;
import com.silita.dao.TbCompanyHighwayGradeMapper;
import com.silita.model.TbCompanyHighwayGrade;
import com.silita.service.ICompanyHighwayGradeService;
import com.silita.utils.DataHandlingUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * tb_company_highway_grade service
 */
@Service
public class CompanyHighwayGradeServiceImpl implements ICompanyHighwayGradeService {

    @Autowired
    TbCompanyHighwayGradeMapper tbCompanyHighwayGradeMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;

    @Override
    public List<Map<String, Object>> getCompanyHighwayGradeList(TbCompanyHighwayGrade grade) {
        List<Map<String, Object>> list = tbCompanyHighwayGradeMapper.queryCompanyHigway(grade);
        String[] assessLevels = null;
        String[] assessOrigins = null;
        List<Map<String, Object>> companyHighwayList = new ArrayList<>();
        Map<String, Object> highwayMap = null;
        if (null != list && list.size() > 0) {
            for (Map<String, Object> map : list) {
                highwayMap = new HashMap<>();
                assessLevels = MapUtils.getString(map, "assessLevel").split(",");
                assessOrigins = MapUtils.getString(map, "assessOrigin").split(",");
                if (null != assessLevels && assessLevels.length > 0) {
                    for (int i = 0; i < assessOrigins.length; i++) {
                        if (Constant.SOURCE_PRO.equals(assessOrigins[i])){
                            highwayMap.put("proAssessLevel",assessLevels[i]);
                            continue;
                        }
                        highwayMap.put("labAssessLevel",assessLevels[i]);
                        continue;
                    }
                }
                highwayMap.put("assessProv",sysAreaMapper.queryAreaName(MapUtils.getString(map,"assessProvCode")));
                highwayMap.put("comId",MapUtils.getString(map,"comId"));
                highwayMap.put("assessYear",MapUtils.getString(map,"assessYear"));
                highwayMap.put("assessProvCode",MapUtils.getString(map,"assessProvCode"));
                companyHighwayList.add(highwayMap);
            }
        }
        return companyHighwayList;
    }

    @Override
    public List<Map<String, Object>> getHighwayProv(Map<String, Object> param) {
        return tbCompanyHighwayGradeMapper.queryAssessPro(MapUtils.getString(param,"comId"));
    }

    @Override
    public Map<String, Object> addHighway(TbCompanyHighwayGrade grade, String username) {
        //判断是否存在
        grade.setAssessOrigin(Constant.SOURCE_LAB);
        TbCompanyHighwayGrade companyHighwayGrade = tbCompanyHighwayGradeMapper.queryCompanyHighwanGradeDetail(grade);
        if(null != companyHighwayGrade){
            tbCompanyHighwayGradeMapper.deleteCompanyHigway(companyHighwayGrade.getPkid());
        }
        grade.setPkid(DataHandlingUtil.getUUID());
        grade.setCreateBy(username);
        grade.setCreated(new Date());
        grade.setAssessOrigin(Constant.SOURCE_LAB);
        tbCompanyHighwayGradeMapper.insertCompanyHigway(grade);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",Constant.CODE_SUCCESS);
        resultMap.put("msg",Constant.MSG_SUCCESS);
        return resultMap;
    }
}
