package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.DicAliasMapper;
import com.silita.dao.DicCommonMapper;
import com.silita.model.DicAlias;
import com.silita.model.DicCommon;
import com.silita.service.IGradeService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.PinYinUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GradeServiceImpl implements IGradeService {

    @Autowired
    DicCommonMapper dicCommonMapper;
    @Autowired
    DicAliasMapper dicAliasMapper;

    @Override
    public List<Map<String, Object>> getGradeList(Map<String, Object> param) {
        //TODO: ªÒ»°∏∏¿‡
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
    public void saveGrade(DicCommon dicCommon, String username) {
        if (null != dicCommon.getId()) {
            dicCommon.setUpdateBy(username);
            dicCommonMapper.updateDicCommonById(dicCommon);
        } else {
            dicCommon.setId(DataHandlingUtil.getUUID());
            dicCommon.setCode("grade_" + PinYinUtil.cn2py(dicCommon.getName()) + "_" + +System.currentTimeMillis());
            dicCommon.setType(Constant.TYPE_QUA_GRADE);
            dicCommon.setCreateBy(username);
            dicCommonMapper.insertDicCommon(dicCommon);
        }
    }

    @Override
    public void delGrade(Map<String, Object> param) {
        String id = MapUtils.getString(param, "id");
        dicCommonMapper.deleteDicCommonByIds(id.split(","));
    }

    @Override
    public void addGradeAlias(DicAlias alias) {
        alias.setId(DataHandlingUtil.getUUID());
        String code = "alias_grade" + PinYinUtil.cn2py(alias.getName()) + "_" + System.currentTimeMillis();
        alias.setCode(code);
        alias.setStdType(Constant.QUAL_LEVEL_SUB);
        dicAliasMapper.insertDicAlias(alias);
    }
}
