package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.DicCommonMapper;
import com.silita.dao.DicQuaAnalysisMapper;
import com.silita.dao.DicQuaMapper;
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
    @Autowired
    DicQuaAnalysisMapper dicQuaAnalysisMapper;
    @Autowired
    DicQuaMapper dicQuaMapper;

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
            param.put("qualCode",quaCode);
            param.put("stdCode", quaCode);
            List<String> list2 = quaGradeMapper.queryRelId(param);//获取该资质拥有的等级
            if (null != list2 && list2.size() > 0) {
                for (String s : list2) {
                    param.put("relId", s);
                    dicQuaAnalysisMapper.deleteAanlysisRelId(param);//删除资质解析组合数据
                }
            }
            quaGradeMapper.deleteRelQuaCode(param);//根据等级qua_code 删除资质管理表达式中的所有有关的数据

            String codes = MapUtils.getString(param, "codes");
            if (StringUtil.isNotEmpty(codes)) {
                String[] split = codes.split(",");
                List<String> list = Arrays.asList(split);
                List<String> list1 = new ArrayList<>();
                if (split.length >= 2) {
                    List<Integer> list3 = new ArrayList<>();
                    for (String s : list) {
                        Integer integer = dicCommonMapper.queryOrderNoByCode(s);
                        list3.add(integer);
                    }
                    String code = dicCommonMapper.queryCodeByOrderNo(Collections.min(list3).toString());
                    for (String s : list) {
                        String nameByCode = dicCommonMapper.getNameByCode(s);
                        if (StringUtil.isNotEmpty(nameByCode)) {
                            if (!code.equals(s)) {
                                param.put("name", nameByCode + "及以上");
                                String codeByName = dicCommonMapper.getCodeByName(param);//根据name获取code
                                if (StringUtil.isNotEmpty(codeByName)) {
                                    list1.add(codeByName);
                                }
                            }
                            param.put("id", DataHandlingUtil.getUUID());
                            param.put("gradeCode", s);
                            quaGradeMapper.insertQuaCrade(param);
                        }
                    }
                    if (null != list1 && list1.size() > 0) {
                        for (String s1 : list1) {
                            param.put("id", DataHandlingUtil.getUUID());
                            param.put("gradeCode", s1);
                            quaGradeMapper.insertQuaCrade(param);
                        }

                        List<Map<String, Object>> list5 = dicQuaMapper.queryQualAnalysisOne(param);
                        if (null != list5 && list5.size() > 0) {
                            param.put("list", list5);
                            dicQuaAnalysisMapper.insertAanlysis(param);
                        }
                        resultMap.put("code", Constant.CODE_SUCCESS);
                        resultMap.put("msg", Constant.MSG_SUCCESS);
                        return resultMap;
                    }
                } else {
                    for (String s : list) {
                        String nameByCode = dicCommonMapper.getNameByCode(s);
                        if (StringUtil.isNotEmpty(nameByCode)) {
                            param.put("id", DataHandlingUtil.getUUID());
                            param.put("gradeCode", s);
                            quaGradeMapper.insertQuaCrade(param);
                            List<Map<String, Object>> list5 = dicQuaMapper.queryQualAnalysisOne(param);
                            if (null != list5 && list5.size() > 0) {
                                param.put("list", list5);
                                dicQuaAnalysisMapper.insertAanlysis(param);
                            }
                            resultMap.put("code", Constant.CODE_SUCCESS);
                            resultMap.put("msg", Constant.MSG_SUCCESS);
                            return resultMap;
                        }
                    }
                }
            }
            param.put("id", DataHandlingUtil.getUUID());
            param.put("gradeCode", "0");
            quaGradeMapper.insertQuaCrade(param);
            List<Map<String, Object>> list5 = dicQuaMapper.queryQualAnalysisOne(param);
            if (null != list5 && list5.size() > 0) {
                param.put("list", list5);
                dicQuaAnalysisMapper.insertAanlysis(param);
            }
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
        } catch (Exception e) {
            logger.error("修改资质名称维护-资质等级", e);
        }
        return resultMap;

    }
}
