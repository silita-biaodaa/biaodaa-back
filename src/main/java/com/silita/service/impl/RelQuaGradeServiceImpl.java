package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.RelQuaGradeMapper;
import com.silita.model.RelQuaGrade;
import com.silita.service.IRelQuaGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RelQuaGradeServiceImpl implements IRelQuaGradeService {

    @Autowired
    RelQuaGradeMapper quaGradeMapper;

    @Override
    public Map<String, Object> addQuaGrade(RelQuaGrade grade) {
        Map<String,Object> resultMap = new HashMap<>();
        Integer count = quaGradeMapper.queryQuaGradeCout(grade);
        if(count > 0){
            resultMap.put("code",Constant.CODE_WARN_400);
            resultMap.put("msg",Constant.MSG_WARN_400);
            return resultMap;
        }
        quaGradeMapper.insertQuaCrade(grade);
        resultMap.put("code",Constant.CODE_SUCCESS);
        resultMap.put("msg",Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public void delQuaGrade(Map<String, Object> param) {
        quaGradeMapper.delQuaCrade(param);
    }
}
