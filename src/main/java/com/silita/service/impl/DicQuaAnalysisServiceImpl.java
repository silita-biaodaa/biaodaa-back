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
/*
    */
/**
     * 添加资质解析数据
     *
     * @param param
     * @return
     *//*

    @Override
    public Map<String, Object> insertQuaAnalysis(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String benchNames = MapUtils.getString(param, "benchNames");
            String names = MapUtils.getString(param, "names");
            boolean benchNameContains = benchNames.contains(",");//判断标准名称是否包含逗号
            boolean nameContains = names.contains(",");//判断别名是否包含逗号
            if (benchNameContains == false) {//标准名称不包含逗号
                resultMap.put("code", "0");
                resultMap.put("msg", "标准名称不存在逗号");
                return resultMap;
            }
            if (nameContains == false) {//别名不包含逗号
                resultMap.put("code", "0");
                resultMap.put("msg", "别名不存在逗号");
                return resultMap;
            }
            String[] split1 = benchNames.split(",");
            String[] split2 = names.split(",");
            String benchName = split1[0];//标准名称
            param.put("benchName", benchName);
            String qualLevel = split1[1];//标准名称等级
            if (StringUtil.isNotEmpty(qualLevel)) {
                param.put("benchNameLevel", qualLevel);
            }
            String name = split2[0];//资质别名

            String levelName = split2[1];//等级别名
            if (StringUtil.isNotEmpty(levelName)) {
                param.put("levelName", levelName);
                param.put("name", levelName);
                Integer integer = dicAliasMapper.queryName(param);
                if (null != integer && integer != 0) {
                    resultMap.put("code", "0");
                    resultMap.put("msg", "别名已存在");
                    return resultMap;
                }
            }
            String quaCode = dicQuaMapper.queryQuaCodeBenchName(param);
            if (StringUtil.isNotEmpty(quaCode)) {
                param.put("name", name);
                Integer integer = dicAliasMapper.queryName(param);
                if (null != integer && integer != 0) {
                    resultMap.put("code", "0");
                    resultMap.put("msg", "别名已存在");
                    return resultMap;
                }
                param.put("quaCode", quaCode);
                Integer integer1 = relQuaGradeMapper.queryQualLevelBoolean(param);//获取该资质是否有等级
                relQuaGradeMapper.deleteIsNotLevel(param);//删除该资质关系表达式中gradeCode为0的
                if (null != integer1 && integer1 > 0) {//判断该资质是否有等级
                    boolean levelBooleanJiaJi = qualLevel.contains("甲级");//判断是否包含甲级
                    boolean levelBooleanTeJi = qualLevel.contains("特级");//判断是否包含特级
                    if (levelBooleanJiaJi == false && levelBooleanTeJi == false) {
                        boolean levelBooleanJYS = qualLevel.contains("及以上");//判断是否包含及以上
                        if (levelBooleanJYS == true) {//包含及以上
                            param.put("name", qualLevel);
                            String codeByName = dicCommonMapper.getCodeByName(param);//根据name获取code
                            if (StringUtil.isNotEmpty(codeByName)) {//判断是否存在等级code
                                param.put("gradeCode", codeByName);
                                insertAilasOrLevel(param, qualLevel, quaCode, name, codeByName);
                            } else {
                                resultMap.put("code", "0");
                                resultMap.put("msg", "等级不存在");
                                return resultMap;
                            }
                            String[] JYS = qualLevel.split("及以上");//以“及以上”进行分割
                            if (null != JYS[0]) {
                                param.put("name", JYS[0]);
                                insertRelQua(param, quaCode);//判断资质表达式中是否存在该等级，如果不存在则添加一条
                            } else {
                                resultMap.put("code", "0");
                                resultMap.put("msg", "等级不存在");
                                return resultMap;
                            }
                        } else {//不包含及以上
                            param.put("name", qualLevel);
                            String codeByName = dicCommonMapper.getCodeByName(param);//根据name获取code
                            if (StringUtil.isNotEmpty(codeByName)) {//判断code是否存在
                                param.put("gradeCode", codeByName);
                                insertAilasOrLevel(param, qualLevel, quaCode, name, codeByName);
                            } else {//不存在code
                                resultMap.put("msg", "输入数据不规范");
                                resultMap.put("code", "0");
                                return resultMap;
                            }
                            param.put("name", qualLevel + "及以上");
                            insertRelQua(param, quaCode);//判断资质表达式中是否存在该等级，如果不存在则添加一条
                        }
                    } else if (levelBooleanJiaJi == true || levelBooleanTeJi == true) {//包含甲级或特级
                        param.put("name", qualLevel);
                        String codeByName = dicCommonMapper.getCodeByName(param);//根据name获取code
                        if (StringUtil.isNotEmpty(codeByName)) {
                            param.put("gradeCode", codeByName);
                            insertAilasOrLevel(param, qualLevel, quaCode, name, codeByName);
                        } else {
                            resultMap.put("code", "0");
                            resultMap.put("msg", "输入数据不规范");
                            return resultMap;
                        }
                    }
                }
                param.put("id", DataHandlingUtil.getUUID());
                param.put("code", "alias_grade_" + PinYinUtil.cn2py(name) + "_" + System.currentTimeMillis());
                param.put("stdCode", quaCode);
                param.put("name", name);
                param.put("stdType", "1");
                dicAliasMapper.insertLevelAilas(param);//添加资质别名
                param.put("quaCode", quaCode);
                param.put("name", name);
                List<Map<String, Object>> list = dicQuaMapper.queryQualAnalysisLists(param);//获取资质解析维护需要添加的数据
                if(null != list && list.size() > 0) {
                    param.put("list", list);
                    dicQuaAnalysisMapper.insertAanlysis(param);//添加资质解析维护数据
                }
                resultMap.put("code", Constant.CODE_SUCCESS);
                resultMap.put("msg", Constant.MSG_SUCCESS);
                return resultMap;
            }
            resultMap.put("code", "0");
            resultMap.put("msg", "标准名称不存在");
        } catch (Exception e) {
            logger.error("添加资质解析词典", e);
        }
        return resultMap;
    }
*/

    /**
     * 删除资质解析数据
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> delQuaAnalysis(Map<String, Object> param) {
        Map<String,Object> resultMap = new HashMap<>();
       /* String ailasId = MapUtils.getString(param, "ailasId");
        param.put("id",ailasId);
        dicAliasMapper.deleteIdAilas(param);
        String levelAilasId = MapUtils.getString(param, "levelAilasId");
        param.put("id",levelAilasId);
        dicAliasMapper.deleteIdAilas(param);*/
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
