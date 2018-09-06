package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.RelQuaGradeMapper;
import com.silita.model.RelQuaGrade;
import com.silita.service.IRelQuaGradeService;
import com.silita.utils.DataHandlingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelQuaGradeServiceImpl implements IRelQuaGradeService {

    @Autowired
    RelQuaGradeMapper quaGradeMapper;

    @Override
    public Map<String, Object> addQuaGrade(RelQuaGrade grade) {
        Map<String, Object> resultMap = new HashMap<>();
        Integer count = 0;
        if (grade.getGradeCode().contains("|")) {
            String[] gradeCode = grade.getGradeCode().split("\\|");
            List<String> codeList = new ArrayList<>();
            for (String str : gradeCode) {
                grade.setGradeCode(str);
                count = quaGradeMapper.queryQuaGradeCout(grade);
                if (count <= 0) {
                    codeList.add(str);
                }
            }
            if (null != codeList && codeList.size() > 0) {
                for (String str : codeList) {
                    grade.setGradeCode(str);
                    grade.setId(DataHandlingUtil.getUUID());
                    quaGradeMapper.insertQuaCrade(grade);
                }
            }
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
            return resultMap;
        }
        count = quaGradeMapper.queryQuaGradeCout(grade);
        if (count > 0) {
            resultMap.put("code", Constant.CODE_WARN_400);
            resultMap.put("msg", Constant.MSG_WARN_400);
            return resultMap;
        }
        grade.setId(DataHandlingUtil.getUUID());
        quaGradeMapper.insertQuaCrade(grade);
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public void delQuaGrade(Map<String, Object> param) {
        quaGradeMapper.delQuaCrade(param);
    }

    @Override
    public List<RelQuaGrade> getQualGradeList(Map<String, Object> param) {
        return quaGradeMapper.queryQuaGrade(param);
    }
}
