package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.*;
import com.silita.model.DicQuaAnalysis;
import com.silita.service.IDicQuaAnalysisService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.stringUtils.PinYinUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class DicQuaAnalysisServiceImpl extends AbstractService implements IDicQuaAnalysisService {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(DicQuaAnalysisServiceImpl.class);
    @Autowired
    private DicQuaAnalysisMapper dicQuaAnalysisMapper;
    @Autowired
    private RelQuaGradeMapper relQuaGradeMapper;
    @Autowired
    private DicAliasMapper dicAliasMapper;
    @Autowired
    private DicCommonMapper dicCommonMapper;

    /**
     * 资质解析列表及筛选
     *
     * @param dicQuaAnalysis
     * @return
     */
    @Override
    public Map<String, Object> getQuaAnalysisList(DicQuaAnalysis dicQuaAnalysis) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("list", dicQuaAnalysisMapper.queryQuaAnalysisList(dicQuaAnalysis));
            resultMap.put("total", dicQuaAnalysisMapper.queryQuaAnalysisListCount(dicQuaAnalysis));
        } catch (Exception e) {
            logger.error("资质解析列表及筛选", e);
        }
        return super.handlePageCount(resultMap, dicQuaAnalysis);
    }
    /**
     * 添加资质解析数据
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> insertQuaAnalysis(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Integer integer = dicQuaAnalysisMapper.queryJointAilas(param);
            if(null != integer && integer > 0){
                resultMap.put("code", "0");
                resultMap.put("msg", "别名已存在");
                return resultMap;
            }
            Integer integer1 = dicQuaAnalysisMapper.queryQuaLevel(param);
            if(null != integer1 && integer1 > 0){
                resultMap.put("code", "0");
                resultMap.put("msg", "标准名称重复");
                return resultMap;
            }

            dicQuaAnalysisMapper.insertAanlysisOne(param);
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
        } catch (Exception e) {
            logger.error("添加资质解析词典", e);
        }
        return resultMap;
    }

    /**
     * 删除资质解析数据
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> delQuaAnalysis(Map<String, Object> param) {
        Map<String,Object> resultMap = new HashMap<>();
        dicQuaAnalysisMapper.deleteAanlysis(param);
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }


    public void insertAilasOrLevel(Map<String, Object> param, String qualLevel, String quaCode, String name, String codeByName) {
        Integer integer2 = relQuaGradeMapper.queryQualLevelBoolean(param);//根据资质code和等级code获取该资质是否用户该等级
        if (null != integer2 && integer2 > 0) {//如果存在该等级，则直接添加等级别名
            param.put("id", DataHandlingUtil.getUUID());
            param.put("code", "alias_grade_" + PinYinUtil.cn2py(qualLevel) + "_" + System.currentTimeMillis());
            param.put("stdCode", quaCode);
            param.put("name", name);
            param.put("stdType", "3");
            dicAliasMapper.insertLevelAilas(param);
        } else {////如果不存在该等级，则先添加资质等级，再添加等级别名
            String uuid = DataHandlingUtil.getUUID();
            param.put("id", uuid);
            param.put("quaCode", quaCode);
            param.put("gradeCode", codeByName);
            relQuaGradeMapper.insertQuaCrade(param);//
            param.put("id", DataHandlingUtil.getUUID());
            param.put("code", "alias_grade_" + PinYinUtil.cn2py(qualLevel) + "_" + System.currentTimeMillis());
            param.put("stdCode", quaCode);
            param.put("name", name);
            param.put("stdType", "3");
            dicAliasMapper.insertLevelAilas(param);//添加等级别名
        }
    }


    public void insertRelQua(Map<String, Object> param, String quaCode) {
        String codeByName2 = dicCommonMapper.getCodeByName(param);
        if (StringUtil.isNotEmpty(codeByName2)) {
            param.put("gradeCode", codeByName2);
            Integer integer3 = relQuaGradeMapper.queryQualLevelBoolean(param);//根据资质code和等级code获取该资质是否用户该等级
            if (null == integer3 || integer3 == 0) {
                String uuid = DataHandlingUtil.getUUID();
                param.put("id", uuid);
                param.put("quaCode", quaCode);
                param.put("gradeCode", codeByName2);
                relQuaGradeMapper.insertQuaCrade(param);//添加资质关系表达式
            }
        }
    }
}
