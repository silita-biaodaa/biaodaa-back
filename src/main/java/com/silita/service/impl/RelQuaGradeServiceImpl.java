package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.DicCommonMapper;
import com.silita.dao.RelQuaGradeMapper;
import com.silita.model.RelQuaGrade;
import com.silita.service.IRelQuaGradeService;
import com.silita.utils.DataHandlingUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

@Service
public class RelQuaGradeServiceImpl implements IRelQuaGradeService {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RelQuaGradeServiceImpl.class);

    @Autowired
    RelQuaGradeMapper quaGradeMapper;
    @Autowired
    DicCommonMapper dicCommonMapper;

    /**
     * 添加资质等级
     *
     * @param grade
     * @return
     */
/*    @Override
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
    }*/

    /**
     * 删除
     *
     * @param param
     */
    @Override
    public void delQuaGrade(Map<String, Object> param) {
        quaGradeMapper.delQuaCrade(param);
    }

    /**
     * 获取资质下的等级
     *
     * @param param
     * @return
     */
    @Override
    public List<RelQuaGrade> getQualGradeList(Map<String, Object> param) {
        return quaGradeMapper.queryQuaGrade(param);
    }

    /**
     * 修改资质名称维护-资质等级
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> updateRelQuaGrade(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String quaCode = MapUtils.getString(param, "quaCode");
            param.put("stdCode", quaCode);
            quaGradeMapper.deleteRelQuaCode(param);//根据等级qua_code 删除资质管理表达式中的所有有关的数据
            String codes = MapUtils.getString(param, "codes");
            if (StringUtil.isNotEmpty(codes)) {
                String[] split = codes.split(",");
                List<String> list = Arrays.asList(split);
                List<String> list1 = new ArrayList<>();
                if (split.length >= 2) {
                    for (String s : list) {
                        String nameByCode = dicCommonMapper.getNameByCode(s);
                        if (StringUtil.isNotEmpty(nameByCode)) {
                            if (!nameByCode.equals("甲级") && !nameByCode.equals("特级")) {
                                param.put("name", nameByCode + "及以上");
                                String codeByName = dicCommonMapper.getCodeByName(param);
                                if (StringUtil.isNotEmpty(codeByName)) {
                                    list1.add(codeByName);
                                    param.put("id", DataHandlingUtil.getUUID());
                                    param.put("gradeCode", s);
                                    quaGradeMapper.insertQuaCrade(param);
                                }
                            }
                        }
                    }
                    if (null != list1 && list1.size() > 0) {
                        for (String s1 : list1) {
                            param.put("id", DataHandlingUtil.getUUID());
                            param.put("gradeCode", s1);
                            quaGradeMapper.insertQuaCrade(param);
                        }
                        resultMap.put("code", Constant.CODE_SUCCESS);
                        resultMap.put("msg", Constant.MSG_SUCCESS);
                        return resultMap;
                    }
                } else {
                    for (String s : list) {
                        String nameByCode = dicCommonMapper.getNameByCode(s);
                        if (StringUtil.isNotEmpty(nameByCode)) {
                            if (nameByCode.equals("特级") || nameByCode.equals("甲级")) {
                                param.put("id", DataHandlingUtil.getUUID());
                                param.put("gradeCode", s);
                                quaGradeMapper.insertQuaCrade(param);
                                resultMap.put("code", Constant.CODE_SUCCESS);
                                resultMap.put("msg", Constant.MSG_SUCCESS);
                                return resultMap;
                            } else {
                                param.put("name", nameByCode + "及以上");
                                String codeByName = dicCommonMapper.getCodeByName(param);
                                if (StringUtil.isNotEmpty(codeByName)) {
                                    list1.add(codeByName);
                                }
                                param.put("id", DataHandlingUtil.getUUID());
                                param.put("gradeCode", s);
                                quaGradeMapper.insertQuaCrade(param);
                            }
                        }
                    }
                    if (null != list1 && list1.size() > 0) {
                        for (String s : list1) {
                            param.put("gradeCode", s);
                            param.put("id", DataHandlingUtil.getUUID());
                            quaGradeMapper.insertQuaCrade(param);
                        }
                        resultMap.put("code", Constant.CODE_SUCCESS);
                        resultMap.put("msg", Constant.MSG_SUCCESS);
                        return resultMap;
                    }
                }
            }
            param.put("id", DataHandlingUtil.getUUID());
            param.put("gradeCode", "0");
            quaGradeMapper.insertQuaCrade(param);
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
        }catch (Exception e){
            logger.error("修改资质名称维护-资质等级",e);
        }
        return resultMap;

    }
}
