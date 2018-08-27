package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.DicAliasMapper;
import com.silita.dao.DicCommonMapper;
import com.silita.dao.RelQuaGradeMapper;
import com.silita.model.DicAlias;
import com.silita.model.DicCommon;
import com.silita.model.RelQuaGrade;
import com.silita.service.IGradeService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.PinYinUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GradeServiceImpl implements IGradeService {

    @Autowired
    DicCommonMapper dicCommonMapper;
    @Autowired
    DicAliasMapper dicAliasMapper;
    @Autowired
    RelQuaGradeMapper relQuaGradeMapper;

    @Override
    public List<Map<String, Object>> getGradeList(Map<String, Object> param) {
        //TODO
        List<Map<String, Object>> parentList = dicCommonMapper.queryParentGrade();
        List<Map<String, Object>> gradeList = null;
        DicAlias alias = null;
        for (Map<String, Object> paren : parentList) {
            paren.put("parentId", paren.get("id"));
            gradeList = dicCommonMapper.queryGradeList(paren);
            if (null != gradeList && gradeList.size() > 0) {
                for (Map<String, Object> grade : gradeList) {
                    alias = new DicAlias();
                    alias.setStdCode(MapUtils.getString(grade, "code"));
                    grade.put("alias", dicAliasMapper.listDicAliasByStdCode(alias));
                }
            }
            paren.put("gradeList", gradeList);
        }
        return parentList;
    }

    @Override
    public Map<String, Object> saveGrade(DicCommon dicCommon, String username) {
        Integer count = 0;
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("name", dicCommon.getName());
        param.put("type", Constant.TYPE_QUA_GRADE);
        if (null != dicCommon.getId()) {
            param.put("id", dicCommon.getId());
            count = dicCommonMapper.queryDicCommCountByName(param);
            if (count > 0) {
                resultMap.put("code", Constant.CODE_WARN_400);
                resultMap.put("msg", Constant.MSG_WARN_400);
                return resultMap;
            }
            dicCommon.setUpdateBy(username);
            dicCommonMapper.updateDicCommonById(dicCommon);
        } else {
            count = dicCommonMapper.queryDicCommCountByName(param);
            if (count > 0) {
                resultMap.put("code", Constant.CODE_WARN_400);
                resultMap.put("msg", Constant.MSG_WARN_400);
                return resultMap;
            }
            dicCommon.setId(DataHandlingUtil.getUUID());
            dicCommon.setCode("grade_" + PinYinUtil.cn2py(dicCommon.getName()) + "_" + +System.currentTimeMillis());
            dicCommon.setType(Constant.TYPE_QUA_GRADE);
            dicCommon.setCreateBy(username);
            dicCommonMapper.insertDicCommon(dicCommon);
        }
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> delGrade(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        String id = MapUtils.getString(param, "id");
        List<DicCommon> dicCommons = dicCommonMapper.listDicCommonByIds(id.split(","));
        DicCommon dic = dicCommons.get(0);
        Integer count = relQuaGradeMapper.quaryGradeCountByCode(dic.getCode());
        if (count > 0) {
            resultMap.put("code", Constant.CODE_WARN_407);
            resultMap.put("msg", Constant.MSG_WARN_407);
            return resultMap;
        }
        dicCommonMapper.deleteDicCommonByIds(id.split(","));
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> addGradeAlias(DicAlias alias) {
        Map<String, Object> resultMap = new HashMap<>();
        //判断名称是否存在
        Map<String, Object> param = new HashMap<>();
        param.put("name", alias.getName());
        param.put("stdType", Constant.GRADE_STD_TYPE);
        Integer count = dicAliasMapper.queryAliasByName(param);
        if (count > 0) {
            resultMap.put("code", Constant.CODE_WARN_400);
            resultMap.put("msg", Constant.MSG_WARN_400);
            return resultMap;
        }
        alias.setId(DataHandlingUtil.getUUID());
        String code = "alias_grade" + PinYinUtil.cn2py(alias.getName()) + "_" + System.currentTimeMillis();
        alias.setCode(code);
        alias.setStdType(Constant.GRADE_STD_TYPE);
        dicAliasMapper.insertDicAlias(alias);
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> getQualGradeList(Map<String, Object> param) {
        //TODO:
        List<Map<String, Object>> parentList = dicCommonMapper.queryParentGrade();
        return parentList;
    }

    @Override
    public List<Map<String, Object>> getSecQualGradeList(Map<String, Object> param) {
        List<Map<String, Object>> gradeList = dicCommonMapper.queryGradeList(param);
        return gradeList;
    }

    @Override
    public Map<String, Object> updateGradeAlias(DicAlias dicAlias) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("id", dicAlias.getId());
        param.put("name", dicAlias.getName());
        param.put("stdType", Constant.GRADE_STD_TYPE);
        Integer count = dicAliasMapper.queryAliasByName(param);
        if (count > 0) {
            resultMap.put("code", Constant.CODE_WARN_400);
            resultMap.put("msg", Constant.MSG_WARN_400);
            return resultMap;
        }
        String code = "alias_grade" + PinYinUtil.cn2py(dicAlias.getName()) + "_" + System.currentTimeMillis();
        dicAlias.setCode(code);
        dicAliasMapper.updateDicAliasById(dicAlias);
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }
}
