package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.DicAliasMapper;
import com.silita.dao.DicQuaMapper;
import com.silita.dao.RelQuaGradeMapper;
import com.silita.model.DicAlias;
import com.silita.model.DicQua;
import com.silita.model.RelQuaGrade;
import com.silita.service.IQualService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.stringUtils.PinYinUtil;
import com.silita.utils.stringUtils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 资质实现类
 */
@Service
public class QualServiceImpl implements IQualService {

    @Autowired
    DicQuaMapper dicQuaMapper;
    @Autowired
    DicAliasMapper dicAliasMapper;
    @Autowired
    RelQuaGradeMapper relQuaGradeMapper;
    @Autowired
    RelQuaGradeMapper quaGradeMapper;

    @Override
    public Map<String, Object> addQual(DicQua qua, String username) {
        Integer count = 0;
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("quaName", qua.getQuaName());
        param.put("parentId",qua.getParentId());
        if (null == qua.getLevel()){
            if (null != qua.getParentId()) {
                qua.setLevel(Constant.QUAL_LEVEL_SUB);
            } else {
                qua.setLevel(Constant.QUAL_LEVEL_PARENT);
            }
        }
        if (null != qua.getId()) {
            param.put("id", qua.getId());
            count = dicQuaMapper.queryQualCountByName(param);
            if (count > 0) {
                resultMap.put("code", Constant.CODE_WARN_400);
                resultMap.put("msg", Constant.MSG_WARN_400);
                return resultMap;
            }
            qua.setUpdateTime(new Date());
            qua.setUpdateBy(username);
            dicQuaMapper.updateDicQual(qua);
        } else {
            count = dicQuaMapper.queryQualCountByName(param);
            if (count > 0) {
                resultMap.put("code", Constant.CODE_WARN_400);
                resultMap.put("msg", Constant.MSG_WARN_400);
                return resultMap;
            }
            qua.setCreateBy(username);
            String qualCode = "qual" + "_" + PinYinUtil.cn2py(qua.getQuaName()) + "_" + System.currentTimeMillis();
            qua.setQuaCode(qualCode);
            if (null == qua.getBizType()){
                qua.setBizType(Constant.BIZ_TYPE_ALL);
            }
            qua.setId(DataHandlingUtil.getUUID());
            qua.setCreateTime(new Date());
            dicQuaMapper.insertDicQual(qua);
        }
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public void delQual(String id) {
        dicQuaMapper.delDicQual(id);
    }

    @Override
    public List<Map<String, Object>> getQualCateList() {
        return dicQuaMapper.queryQualCateList();
    }

    @Override
    public List<DicQua> getDicQuaList(Map<String, Object> param) {
        return dicQuaMapper.queryDicQuaList(param);
    }

    @Override
    public Map<String, Object> aliasAdd(DicAlias alias) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("stdType", Constant.QUAL_LEVEL_PARENT);
        param.put("name", alias.getName());
        Integer count = dicAliasMapper.queryAliasByName(param);
        if (count > 0) {
            resultMap.put("code", Constant.CODE_WARN_400);
            resultMap.put("msg", Constant.MSG_WARN_400);
            return resultMap;
        }
        alias.setId(DataHandlingUtil.getUUID());
        String code = "alias_qual_" + PinYinUtil.cn2py(alias.getName()) + "_" + System.currentTimeMillis();
        alias.setCode(code);
        alias.setStdType(Constant.QUAL_LEVEL_PARENT);
        dicAliasMapper.insertDicAlias(alias);
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public void addQuaAlias(List<DicAlias> dicAliasList) {
        Map<String, Object> param = new HashMap<>();
        param.put("stdType", Constant.QUAL_LEVEL_PARENT);
        for (DicAlias alias : dicAliasList) {
            dicAliasMapper.insertDicAlias(alias);
        }
    }

    @Override
    public Map<String, Object> updateQuaAlias(DicAlias alias) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("stdType", Constant.QUAL_LEVEL_PARENT);
        param.put("name", alias.getName());
        param.put("id", alias.getId());
        Integer count = dicAliasMapper.queryAliasByName(param);
        if (count > 0) {
            resultMap.put("code", Constant.CODE_WARN_400);
            resultMap.put("msg", Constant.MSG_WARN_400);
            return resultMap;
        }
        String code = "alias_qual_" + PinYinUtil.cn2py(alias.getName()) + "_" + System.currentTimeMillis();
        alias.setCode(code);
        dicAliasMapper.updateDicAliasById(alias);
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> qualGradeList() {
        List<Map<String, Object>> qualCradeList = dicQuaMapper.queryQualCateList();
        List<DicQua> qualList = new ArrayList<>();
        if (null != qualCradeList && qualCradeList.size() > 0) {
            Map<String, Object> qualMap = new HashMap<>();
            for (Map<String, Object> qual : qualCradeList) {
                qualMap.put("parentId", qual.get("id"));
                qualList = dicQuaMapper.queryDicQuaList(qualMap);
                if (null != qualList && qualList.size() > 0) {
                    for (DicQua qua : qualList) {
                        qualMap.put("quaCode",qua.getQuaCode());
                        qualMap.put("bizType",Constant.BIZ_TYPE_COMPANY);
                        qua.setGradeList(relQuaGradeMapper.queryQuaGrade(qualMap));
                    }
                }
                qual.put("qualList",qualList);
            }
        }
        return qualCradeList;
    }

    @Override
    @Cacheable(value = "listQualCache")
    public List<Map<String,Object>> listQual() {
        List<Map<String,Object>> qualCateList = new ArrayList<>();
        List<Map<String,Object>> qualList;
        List<Map<String,Object>> qualGradeList;
        List<Map<String, Object>> list = dicQuaMapper.queryQualCateList();
        Map dicMap;
        Map qualCateMap;
        Map qualMap;
        Map qualGradeMap;
        //资质类型列表
        for (int i = 0; i < list.size(); i++) {
            dicMap = new HashMap<String, Object>();
            dicMap.put("parentId", list.get(i).get("id"));
            List<DicQua> dicQuas = dicQuaMapper.queryDicQuaList(dicMap);
            Map gradeMap;
            qualCateMap = new HashMap();
            qualList = new ArrayList<>();
            qualCateMap.put("key",list.get(i).get("id"));
            qualCateMap.put("value",list.get(i).get("quaName"));
            //资质名称列表
            for (int j = 0; j < dicQuas.size(); j++) {
                qualMap = new HashMap();
                qualGradeList = new ArrayList<>();
                qualMap.put("key",dicQuas.get(j).getId());
                qualMap.put("value",dicQuas.get(j).getQuaName());
                gradeMap = new HashMap<String, Object>();
                gradeMap.put("quaCode", dicQuas.get(j).getQuaCode());
                gradeMap.put("bizType", "1");
                List<RelQuaGrade> relQuaGrades = quaGradeMapper.queryQuaGrade(gradeMap);
                //公告资质等级
                for(int k = 0;k<relQuaGrades.size();k++){
                    qualGradeMap = new HashMap();
                    qualGradeMap.put("key",relQuaGrades.get(k).getGradeId());
                    qualGradeMap.put("value",relQuaGrades.get(k).getName());
                    qualGradeList.add(qualGradeMap);
                }
                qualMap.put("qualList",qualGradeList);
                qualList.add(qualMap);
            }
            qualCateMap.put("qualList",qualList);
            qualCateList.add(qualCateMap);
        }
        return qualCateList;
    }
}
