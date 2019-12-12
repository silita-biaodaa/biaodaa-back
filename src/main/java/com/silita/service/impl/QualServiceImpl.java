package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.*;
import com.silita.model.DicAlias;
import com.silita.model.DicQua;
import com.silita.model.RelQuaGrade;
import com.silita.service.IQualService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.stringUtils.PinYinUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

/**
 * 资质实现类
 */
@Service
public class QualServiceImpl extends AbstractService implements IQualService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(QualServiceImpl.class);
    @Autowired
    DicQuaMapper dicQuaMapper;
    @Autowired
    DicAliasMapper dicAliasMapper;
    @Autowired
    RelQuaGradeMapper relQuaGradeMapper;
    @Autowired
    DicCommonMapper dicCommonMapper;
    @Autowired
    DicQuaAnalysisMapper dicQuaAnalysisMapper;

    @Override
    public void addQualTest(Map<String, Object> param) {
        dicQuaMapper.insertDicQual(param);
    }

    /**
     * 添加资质
     *
     * @param param
     */
    @Override
    public Map<String, Object> addQual(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String qualType = MapUtils.getString(param, "qualType");//资质类别
            String quaBig = MapUtils.getString(param, "quaBig");//资质大类
            String quaTiny = MapUtils.getString(param, "quaTiny");//资质小类
            String quaName = MapUtils.getString(param, "quaName");//资质名称
            String benchName = MapUtils.getString(param, "benchName");//资质标准名称

            Integer integer = dicQuaMapper.querySingleQuaName(param);
            if (null != integer && integer != 0) {
                resultMap.put("code", "0");
                resultMap.put("msg", "资质名称已存在");
                return resultMap;
            }

            Integer integer1 = dicQuaMapper.querySingleBenchName(param);
            if (null != integer1 && integer1 != 0) {
                resultMap.put("code", "0");
                resultMap.put("msg", "资质标准名称已存在");
                return resultMap;
            }
            if (StringUtil.isNotEmpty(quaBig) && StringUtil.isEmpty(quaTiny)) {
                param.put("quaMajor", quaBig);
                param.put("pid", qualType);
                Map<String, Object> map = dicQuaMapper.queryQuaLevel(param);
                Integer level = MapUtils.getInteger(map, "level");
                if (null != map && map.size() > 0) {
                    if (level == 2) {
                        String id = MapUtils.getString(map, "id");
                        String uuid = DataHandlingUtil.getUUID();
                        param.put("id", uuid);
                        param.put("parentId", id);
                        String qualCode = "qual" + "_" + PinYinUtil.cn2py(quaName) + "_" + System.currentTimeMillis();
                        param.put("quaCode", qualCode);
                        param.put("stdCode", qualCode);
                        param.put("level", 3);
                        param.put("quaName", quaName);
                        param.put("benchName", benchName);
                        param.put("qualId", uuid);
                        dicQuaMapper.insertDicQual(param);
                    }
                } else if (null == map || map.size() > 0) {
                    String uuid = DataHandlingUtil.getUUID();
                    param.put("id", uuid);
                    param.put("parentId", qualType);
                    String qualCode = "qual" + "_" + PinYinUtil.cn2py(quaBig) + "_" + System.currentTimeMillis();
                    param.put("quaCode", qualCode);
                    param.put("level", 2);
                    param.put("quaName", quaBig);
                    param.put("benchName", "");
                    dicQuaMapper.insertDicQual(param);
                    String uuid1 = DataHandlingUtil.getUUID();
                    param.put("id", uuid1);
                    param.put("parentId", uuid);
                    String qualCode2 = "qual" + "_" + PinYinUtil.cn2py(quaName) + "_" + System.currentTimeMillis();
                    param.put("quaCode", qualCode2);
                    param.put("stdCode", qualCode2);
                    param.put("level", 3);
                    param.put("quaName", quaName);
                    param.put("benchName", benchName);
                    param.put("qualId", uuid1);
                    dicQuaMapper.insertDicQual(param);
                }
            } else if (StringUtil.isNotEmpty(quaBig) && StringUtil.isNotEmpty(quaTiny)) {
                param.put("quaMajor", quaBig);
                param.put("pid", qualType);
                Map<String, Object> map = dicQuaMapper.queryQuaLevel(param);
                Integer level = MapUtils.getInteger(map, "level");
                if (null != map && map.size() > 0) {
                    if (level == 2) {
                        String id = MapUtils.getString(map, "id");
                        param.put("quaMajor", quaTiny);
                        param.put("pid", id);
                        Map<String, Object> map2 = dicQuaMapper.queryQuaLevel(param);
                        Integer level2 = MapUtils.getInteger(map2, "level");
                        if (null != map2 && map2.size() > 0) {
                            if (level2 == 3) {
                                String id2 = MapUtils.getString(map2, "id");
                                String uuid = DataHandlingUtil.getUUID();
                                param.put("id", uuid);
                                param.put("parentId", id2);
                                String qualCode = "qual" + "_" + PinYinUtil.cn2py(quaName) + "_" + System.currentTimeMillis();
                                param.put("level", 4);
                                param.put("quaCode", qualCode);
                                param.put("stdCode", qualCode);
                                param.put("quaName", quaName);
                                param.put("benchName", benchName);
                                param.put("qualId", uuid);
                                dicQuaMapper.insertDicQual(param);
                            }
                        } else {
                            String uuid1 = DataHandlingUtil.getUUID();
                            param.put("id", uuid1);
                            param.put("parentId", id);
                            String qualCode2 = "qual" + "_" + PinYinUtil.cn2py(quaTiny) + "_" + System.currentTimeMillis();
                            param.put("quaCode", qualCode2);
                            param.put("level", 3);
                            param.put("quaName", quaTiny);
                            param.put("benchName", "");
                            dicQuaMapper.insertDicQual(param);
                            String uuid = DataHandlingUtil.getUUID();
                            param.put("id", uuid);
                            param.put("parentId", uuid1);
                            String qualCode = "qual" + "_" + PinYinUtil.cn2py(quaName) + "_" + System.currentTimeMillis();
                            param.put("level", 4);
                            param.put("quaName", quaName);
                            param.put("benchName", benchName);
                            param.put("stdCode", qualCode);
                            param.put("quaCode", qualCode);
                            param.put("stdCodes", qualCode);
                            param.put("qualId", uuid);
                            dicQuaMapper.insertDicQual(param);
                        }
                    }
                } else if (null == map || map.size() > 0) {
                    String uuid = DataHandlingUtil.getUUID();
                    param.put("id", uuid);
                    param.put("parentId", qualType);
                    String qualCode = "qual" + "_" + PinYinUtil.cn2py(quaBig) + "_" + System.currentTimeMillis();
                    param.put("quaCode", qualCode);
                    param.put("level", 2);
                    param.put("quaName", quaBig);
                    param.put("benchName", "");
                    dicQuaMapper.insertDicQual(param);
                    String uuid1 = DataHandlingUtil.getUUID();
                    param.put("id", uuid1);
                    param.put("parentId", uuid);
                    String qualCode2 = "qual" + "_" + PinYinUtil.cn2py(quaTiny) + "_" + System.currentTimeMillis();
                    param.put("quaCode", qualCode2);
                    param.put("level", 3);
                    param.put("quaName", quaTiny);
                    param.put("benchName", "");
                    dicQuaMapper.insertDicQual(param);
                    String uuid2 = DataHandlingUtil.getUUID();
                    param.put("id", uuid2);
                    param.put("parentId", uuid1);
                    String qualCode3 = "qual" + "_" + PinYinUtil.cn2py(quaName) + "_" + System.currentTimeMillis();
                    param.put("quaCode", qualCode3);
                    param.put("stdCode", qualCode3);
                    param.put("level", 4);
                    param.put("quaName", quaName);
                    param.put("benchName", benchName);
                    param.put("qualId", uuid2);
                    dicQuaMapper.insertDicQual(param);

                }
            } else {
                String uuid = DataHandlingUtil.getUUID();
                param.put("id", uuid);
                param.put("parentId", qualType);
                String qualCode3 = "qual" + "_" + PinYinUtil.cn2py(quaName) + "_" + System.currentTimeMillis();
                param.put("quaCode", qualCode3);
                param.put("stdCode", qualCode3);
                param.put("level", 2);
                param.put("quaName", quaName);
                param.put("benchName", benchName);
                param.put("qualId", uuid);
                dicQuaMapper.insertDicQual(param);
            }

            String levelType = MapUtils.getString(param, "levelType");
            if (levelType.equals("0")) {
                param.put("gradeCode", "0");
                param.put("id", DataHandlingUtil.getUUID());
                param.put("bizType", "");
                relQuaGradeMapper.insertQuaCrade(param);//添加资质关系表达式
            } else {
                List<String> list = dicCommonMapper.queryLevelCode(param);
                for (String s : list) {
                    param.put("id", DataHandlingUtil.getUUID());
                    param.put("gradeCode", s);
                    param.put("bizType", "");
                    relQuaGradeMapper.insertQuaCrade(param);
                }
            }
            //添加别名
            param.put("id", DataHandlingUtil.getUUID());
            param.put("name", benchName);
            param.put("code", "alias_grade_" + PinYinUtil.cn2py(benchName) + "_" + System.currentTimeMillis());
            param.put("stdType", "1");
            dicAliasMapper.insertLevelAilas(param);

            //获取资质解析组合数据
            String quaId = MapUtils.getString(param, "quaId");
            param.put("id", quaId);
            addQuaAnalysis(param, levelType);
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
        } catch (Exception e) {

            logger.error("添加资质", e);
        }
        return resultMap;
    }



    /**
     * 删除资质
     *
     * @param param
     */
    @Override
    public void delQual(Map<String, Object> param) {
        try {
            String code = dicQuaMapper.queryCode(param);//获取资质code
            param.put("stdCode", code);
            param.put("quaCode", code);
            List<String> list = relQuaGradeMapper.queryRelId(param);
            if (null != list && list.size() > 0) {
                for (String s : list) {
                    param.put("relId", s);
                    dicQuaAnalysisMapper.deleteAanlysisRelId(param);
                }
            }
            dicAliasMapper.deleteAilas(param);//根据code删除别名
            relQuaGradeMapper.deleteRelQuaCode(param);
            dicQuaMapper.delDicQual(param);//根据id删除资质
        } catch (Exception e) {
            logger.error("删除资质", e);
        }
    }

    /**
     * 修改资质
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> updQuals(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String quaId = MapUtils.getString(param, "quaId");//资质id
            String qualType = MapUtils.getString(param, "qualType");//资质类别
            String quaBig = MapUtils.getString(param, "quaBig");//资质大类
            String quaTiny = MapUtils.getString(param, "quaTiny");//资质小类
            String quaName = MapUtils.getString(param, "quaName");//资质名称
            String benchName = MapUtils.getString(param, "benchName");//资质标准名称
            String quaCode = MapUtils.getString(param, "quaCode");
            String benchNameAilas = dicQuaMapper.querySingleBenchNames(param);
            Integer integer = dicQuaMapper.querySingleQuaName(param);
            if (null != integer && integer != 0) {
                String name = dicQuaMapper.querySingleQuaNames(param);
                if (StringUtil.isNotEmpty(name)) {
                    if (!name.equals(quaName)) {
                        resultMap.put("code", "0");
                        resultMap.put("msg", "资质名称已存在");
                        return resultMap;
                    }
                }
            }
            Integer integer1 = dicQuaMapper.querySingleBenchName(param);
            if (null != integer1 && integer1 != 0) {
                String name2 = dicQuaMapper.querySingleBenchNames(param);
                if (StringUtil.isNotEmpty(name2)) {
                    if (!name2.equals(benchName)) {
                        Integer integer2 = dicQuaMapper.querySingleBenchName(param);
                        if (null != integer2 && integer2 != 0) {
                            resultMap.put("code", "0");
                            resultMap.put("msg", "资质标准名称已存在");
                            return resultMap;
                        }
                    }
                }
            }
            List<String> list = relQuaGradeMapper.queryRelId(param);
            if (null != list && list.size() > 0) {
                for (String s : list) {
                    param.put("relId", s);
                    dicQuaAnalysisMapper.deleteAanlysisRelId(param);
                }
            }

            if (StringUtil.isNotEmpty(quaBig) && StringUtil.isEmpty(quaTiny)) {
                param.put("quaMajor", quaBig);
                param.put("pid", qualType);
                Map<String, Object> map = dicQuaMapper.queryQuaLevel(param);
                Integer level = MapUtils.getInteger(map, "level");
                if (null != map && map.size() > 0) {
                    if (level == 2) {
                        String id = MapUtils.getString(map, "id");
                        param.put("id", quaId);
                        param.put("parentId", id);
                        param.put("level", 3);
                        param.put("quaName", quaName);
                        param.put("benchName", benchName);
                        dicQuaMapper.updateDicQual(param);//修改资质
                    }
                } else if (null == map || map.size() <= 0) {
                    String uuid = DataHandlingUtil.getUUID();
                    param.put("id", uuid);
                    param.put("parentId", qualType);
                    String qualCode = "qual" + "_" + PinYinUtil.cn2py(quaBig) + "_" + System.currentTimeMillis();
                    param.put("quaCode", qualCode);
                    param.put("level", 2);
                    param.put("quaName", quaBig);
                    param.put("benchName", "");
                    dicQuaMapper.insertDicQual(param);
                    param.put("id", quaId);
                    param.put("parentId", uuid);
                    param.put("level", 3);
                    param.put("quaName", quaName);
                    param.put("benchName", benchName);
                    param.remove("quaCode");
                    dicQuaMapper.updateDicQual(param);//修改资质
                }
            } else if (StringUtil.isNotEmpty(quaBig) && StringUtil.isNotEmpty(quaTiny)) {
                param.put("quaMajor", quaBig);
                param.put("pid", qualType);
                Map<String, Object> map = dicQuaMapper.queryQuaLevel(param);
                Integer level = MapUtils.getInteger(map, "level");
                if (null != map && map.size() > 0) {
                    if (level == 2) {
                        String id = MapUtils.getString(map, "id");
                        param.put("quaMajor", quaTiny);
                        param.put("pid", id);
                        Map<String, Object> map2 = dicQuaMapper.queryQuaLevel(param);
                        Integer level2 = MapUtils.getInteger(map2, "level");
                        if (null != map2 && map2.size() > 0) {
                            String id1 = MapUtils.getString(map2, "id");
                            if (level2 == 3 && !id1.equals(quaId)) {
                                String id2 = MapUtils.getString(map2, "id");
                                param.put("id", quaId);
                                param.put("parentId", id2);
                                param.put("level", 4);
                                param.put("quaName", quaName);
                                param.put("benchName", benchName);
                                dicQuaMapper.updateDicQual(param);//修改资质
                            } else {
                                String uuid1 = DataHandlingUtil.getUUID();
                                param.put("id", uuid1);
                                param.put("parentId", id);
                                String qualCode2 = "qual" + "_" + PinYinUtil.cn2py(quaTiny) + "_" + System.currentTimeMillis();
                                param.put("quaCode", qualCode2);
                                param.put("level", 3);
                                param.put("quaName", quaTiny);
                                param.put("benchName", "");
                                dicQuaMapper.insertDicQual(param);
                                param.put("id", quaId);
                                param.put("level", 4);
                                param.put("parentId", uuid1);
                                param.put("quaName", quaName);
                                param.put("benchName", benchName);
                                param.remove("quaCode");
                                dicQuaMapper.updateDicQual(param);//修改资质
                            }
                        } else {
                            String uuid1 = DataHandlingUtil.getUUID();
                            param.put("id", uuid1);
                            param.put("parentId", id);
                            String qualCode2 = "qual" + "_" + PinYinUtil.cn2py(quaTiny) + "_" + System.currentTimeMillis();
                            param.put("quaCode", qualCode2);
                            param.put("level", 3);
                            param.put("quaName", quaTiny);
                            param.put("benchName", "");
                            dicQuaMapper.insertDicQual(param);
                            param.put("id", quaId);
                            param.put("parentId", uuid1);
                            param.put("level", 4);
                            param.put("quaName", quaName);
                            param.put("benchName", benchName);
                            param.remove("quaCode");
                            dicQuaMapper.updateDicQual(param);//修改资质
                        }
                    }
                } else if (null == map || map.size() == 0) {
                    String uuid = DataHandlingUtil.getUUID();
                    param.put("id", uuid);
                    param.put("parentId", qualType);
                    String qualCode = "qual" + "_" + PinYinUtil.cn2py(quaBig) + "_" + System.currentTimeMillis();
                    param.put("quaCode", qualCode);
                    param.put("level", 2);
                    param.put("quaName", quaBig);
                    param.put("benchName", "");
                    dicQuaMapper.insertDicQual(param);
                    String uuid1 = DataHandlingUtil.getUUID();
                    param.put("id", uuid1);
                    param.put("parentId", uuid);
                    String qualCode2 = "qual" + "_" + PinYinUtil.cn2py(quaTiny) + "_" + System.currentTimeMillis();
                    param.put("quaCode", qualCode2);
                    param.put("level", 3);
                    param.put("quaName", quaTiny);
                    param.put("benchName", "");
                    dicQuaMapper.insertDicQual(param);
                    param.put("id", quaId);
                    param.put("parentId", uuid1);
                    param.put("level", 4);
                    param.put("quaName", quaName);
                    param.put("benchName", benchName);
                    param.remove("quaCode");
                    dicQuaMapper.updateDicQual(param);//修改资质
                }
            } else {
                param.put("id", quaId);
                param.put("parentId", qualType);
                param.put("level", 2);
                param.put("quaName", quaName);
                param.put("benchName", benchName);
                dicQuaMapper.updateDicQual(param);//修改资质
            }
            String levelType = MapUtils.getString(param, "levelType");
            param.put("stdCode",quaCode);
            getLevel(levelType, param);
            if (StringUtil.isNotEmpty(benchNameAilas)) {
                param.put("benchName", benchName);
                param.put("benchNameAilas", benchNameAilas);
                dicAliasMapper.updateName(param);
            }
            if(StringUtil.isNotEmpty(quaCode)){
                param.put("qualCode",quaCode);
            }
            param.put("qualId",quaId);
            addQuaAnalysis(param, levelType);
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
        } catch (Exception e) {
            logger.error("修改资质", e);
        }
        return resultMap;
    }

    public void addQuaAnalysis(Map<String, Object> param, String levelType) {
        if (!levelType.equals("0")) {
            List<Map<String, Object>> list = dicQuaMapper.queryQualAnalysisOne(param);
            if (null != list && list.size() > 0) {
                param.put("list", list);
                dicQuaAnalysisMapper.insertAanlysis(param);
            }
        } else {
            List<Map<String, Object>> list = dicQuaMapper.queryQualAnalysisTow(param);
            if (null != list && list.size() > 0) {
                param.put("list", list);
                dicQuaAnalysisMapper.insertAanlysis(param);
            }
        }
    }

    /**
     * 获取资质类别
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getQualCateList() {
        return dicQuaMapper.queryQualCateList();
    }

    /**
     * 获取资质列表
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getDicQuaListMaps(Map<String, Object> param) {
        String ids = MapUtils.getString(param, "ids");
        String[] split = ids.split(",");
        if (split.length >= 1) {
            param.put("id", split[0]);
        }
        if (split.length >= 2) {
            param.put("two", split[1]);
        }
        if (split.length >= 3) {
            param.put("three", split[2]);
        }
        if (split.length >= 4) {
            param.put("four", split[3]);
        }


        String benchName1 = MapUtils.getString(param, "benchName");
        Map<String, Object> result = new HashMap<>();
        if (StringUtil.isNotEmpty(benchName1)) {
            List<String> list = dicQuaMapper.queryBenchNames(param);
            for (String s : list) {
                result.put(s, "0");
            }
        }


        //param.put("noticeLevel","1");
        param.put("zzIdOne", "");
        List<Map<String, Object>> list = dicQuaMapper.queryQuaOne(param);
        List<Map<String, Object>> oneQuaListMap = new ArrayList<>();
        //遍历资质一级
        for (Map<String, Object> map : list) {
            //把一级资质放入oneQuaMap中
            String one = (String) map.get("id");
            param.put("zzIdOne", one);
            List<Map<String, Object>> list1 = dicQuaMapper.queryQuaTwo(param);
            for (Map<String, Object> map2 : list1) {
                Map<String, Object> towQuaMap = new HashMap<>();
                String tow = (String) map2.get("id");
                param.put("zzIdOne", tow);
                if (null == MapUtils.getString(map2, "benchName")) {
                    List<Map<String, Object>> list2 = dicQuaMapper.queryQuaThree(param);
                    for (Map<String, Object> map3 : list2) {
                        Map<String, Object> threeQuaMap = new HashMap<>();
                        String three = (String) map3.get("id");
                        param.put("zzIdOne", three);
                        if (null == MapUtils.getString(map3, "benchName")) {
                            List<Map<String, Object>> list3 = dicQuaMapper.queryQuaFout(param);
                            for (Map<String, Object> map4 : list3) {
                                Map<String, Object> fourQuaMap = new HashMap<>();
                                String benchName = (String) map4.get("benchName");
                                if (StringUtil.isEmpty(benchName1)) {
                                    if (StringUtils.isNotEmpty(benchName)) {
                                        param.put("quaCode", map4.get("quaCode"));
                                        String s = dicCommonMapper.queryParentId(param);
                                        fourQuaMap.put("levelType", s);
                                        fourQuaMap.put("qualType", map.get("quaName"));
                                        fourQuaMap.put("quaBig", map2.get("quaName"));
                                        fourQuaMap.put("quaTiny", map3.get("quaName"));
                                        fourQuaMap.put("id", map4.get("id"));
                                        fourQuaMap.put("quaCode", map4.get("quaCode"));
                                        fourQuaMap.put("quaName", map4.get("quaName"));
                                        fourQuaMap.put("benchName", map4.get("benchName"));
                                        fourQuaMap.put("bizType", map4.get("bizType"));
                                        oneQuaListMap.add(fourQuaMap);
                                    }
                                } else {
                                    String benchName2 = MapUtils.getString(result, MapUtils.getString(map4, "benchName"));
                                    if (StringUtil.isNotEmpty(benchName2)) {
                                        if (StringUtils.isNotEmpty(benchName)) {
                                            param.put("quaCode", map4.get("quaCode"));
                                            String s = dicCommonMapper.queryParentId(param);
                                            fourQuaMap.put("levelType", s);
                                            fourQuaMap.put("qualType", map.get("quaName"));
                                            fourQuaMap.put("quaBig", map2.get("quaName"));
                                            fourQuaMap.put("quaTiny", map3.get("quaName"));
                                            fourQuaMap.put("id", map4.get("id"));
                                            fourQuaMap.put("benchName", map4.get("benchName"));
                                            fourQuaMap.put("quaCode", map4.get("quaCode"));
                                            fourQuaMap.put("quaName", map4.get("quaName"));
                                            fourQuaMap.put("bizType", map4.get("bizType"));
                                            oneQuaListMap.add(fourQuaMap);
                                        }
                                    }
                                }
                            }
                        }
                        String benchName = (String) map3.get("benchName");
                        if (StringUtil.isEmpty(benchName1)) {
                            if (StringUtil.isNotEmpty(benchName)) {
                                param.put("quaCode", map3.get("quaCode"));
                                String s = dicCommonMapper.queryParentId(param);
                                threeQuaMap.put("levelType", s);
                                threeQuaMap.put("qualType", map.get("quaName"));
                                threeQuaMap.put("quaBig", map2.get("quaName"));
                                threeQuaMap.put("quaTiny", "");
                                threeQuaMap.put("quaCode", map3.get("quaCode"));
                                threeQuaMap.put("id", map3.get("id"));
                                threeQuaMap.put("quaName", map3.get("quaName"));
                                threeQuaMap.put("benchName", map3.get("benchName"));
                                threeQuaMap.put("bizType", map3.get("bizType"));
                                oneQuaListMap.add(threeQuaMap);
                            }
                        } else {
                            String benchName2 = MapUtils.getString(result, MapUtils.getString(map3, "benchName"));
                            if (StringUtil.isNotEmpty(benchName2)) {
                                if (StringUtils.isNotEmpty(benchName)) {
                                    param.put("quaCode", map3.get("quaCode"));
                                    String s = dicCommonMapper.queryParentId(param);
                                    threeQuaMap.put("levelType", s);
                                    threeQuaMap.put("qualType", map.get("quaName"));
                                    threeQuaMap.put("quaBig", map2.get("quaName"));
                                    threeQuaMap.put("quaTiny", "");
                                    threeQuaMap.put("quaCode", map3.get("quaCode"));
                                    threeQuaMap.put("benchName", map3.get("benchName"));
                                    threeQuaMap.put("quaName", map3.get("quaName"));
                                    threeQuaMap.put("id", map3.get("id"));
                                    threeQuaMap.put("bizType", map3.get("bizType"));
                                    oneQuaListMap.add(threeQuaMap);
                                }
                            }
                        }
                    }
                }
                String benchName = (String) map2.get("benchName");
                if (StringUtil.isEmpty(benchName1)) {
                    if (StringUtil.isNotEmpty(benchName)) {
                        param.put("quaCode", map2.get("quaCode"));
                        String s = dicCommonMapper.queryParentId(param);
                        towQuaMap.put("levelType", s);
                        towQuaMap.put("qualType", map.get("quaName"));
                        towQuaMap.put("quaBig", "");
                        towQuaMap.put("quaTiny", "");
                        towQuaMap.put("quaCode", map2.get("quaCode"));
                        towQuaMap.put("id", map2.get("id"));
                        towQuaMap.put("benchName", map2.get("benchName"));
                        towQuaMap.put("quaName", map2.get("quaName"));
                        towQuaMap.put("bizType", map2.get("bizType"));
                        oneQuaListMap.add(towQuaMap);
                    }
                } else {
                    String benchName2 = MapUtils.getString(result, MapUtils.getString(map2, "benchName"));
                    if (StringUtil.isNotEmpty(benchName2)) {
                        if (StringUtils.isNotEmpty(benchName)) {
                            param.put("quaCode", map2.get("quaCode"));
                            String s = dicCommonMapper.queryParentId(param);
                            towQuaMap.put("levelType", s);
                            towQuaMap.put("qualType", map.get("quaName"));
                            towQuaMap.put("quaBig", "");
                            towQuaMap.put("quaTiny", "");
                            towQuaMap.put("quaCode", map2.get("quaCode"));
                            towQuaMap.put("benchName", map2.get("benchName"));
                            towQuaMap.put("quaName", map2.get("quaName"));
                            towQuaMap.put("id", map2.get("id"));
                            towQuaMap.put("bizType", map2.get("bizType"));
                            oneQuaListMap.add(towQuaMap);
                        }
                    }
                }
            }
        }
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        return super.getPagingResultMap(oneQuaListMap, pageNo, pageSize);
    }

    /**
     * 添加资质别名
     *
     * @param alias
     */
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
        String stdCode = alias.getStdCode();
        param.put("qualCode", stdCode);
        param.put("qualAilasName", alias.getName());
        Integer integer = relQuaGradeMapper.queryRelQuaGradeCount(param);
        if (null != integer && integer > 0) {
            List<Map<String, Object>> list = dicQuaMapper.queryQualAnalysisOne(param);
            if (null != list && list.size() > 0) {
                param.put("list", list);
                dicQuaAnalysisMapper.insertAanlysis(param);
            }
        } else {
            List<Map<String, Object>> list = dicQuaMapper.queryQualAnalysisTow(param);
            if (null != list && list.size() > 0) {
                param.put("list", list);
                dicQuaAnalysisMapper.insertAanlysis(param);
            }
        }
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

    /**
     * 添加资质别名,去重
     *
     * @param alias
     */
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

    /**
     * 获取全部资质等级
     *
     * @return
     */
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
                        qualMap.put("quaCode", qua.getQuaCode());
                        qualMap.put("bizType", Constant.BIZ_TYPE_COMPANY);
                        qua.setGradeList(relQuaGradeMapper.queryQuaGrade(qualMap));
                    }
                }
                qual.put("qualList", qualList);
            }
        }
        return qualCradeList;
    }


    @Override
    @Cacheable(value = "listQualCache")
    public List<Map<String, Object>> listQual() {
        List<Map<String, Object>> qualCateList = new ArrayList<>();
        List<Map<String, Object>> qualList;
        List<Map<String, Object>> qualGradeList;
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
            qualCateMap.put("key", list.get(i).get("id"));
            qualCateMap.put("value", list.get(i).get("quaName"));
            //资质名称列表
            for (int j = 0; j < dicQuas.size(); j++) {
                qualMap = new HashMap();
                qualGradeList = new ArrayList<>();
                qualMap.put("key", dicQuas.get(j).getId());
                qualMap.put("value", dicQuas.get(j).getQuaName());
                gradeMap = new HashMap<String, Object>();
                gradeMap.put("quaCode", dicQuas.get(j).getQuaCode());
                gradeMap.put("bizType", "1");
                List<RelQuaGrade> relQuaGrades = relQuaGradeMapper.queryQuaGrade(gradeMap);
                //公告资质等级
                for (int k = 0; k < relQuaGrades.size(); k++) {
                    qualGradeMap = new HashMap();
                    qualGradeMap.put("key", relQuaGrades.get(k).getGradeId());
                    qualGradeMap.put("value", relQuaGrades.get(k).getName());
                    qualGradeList.add(qualGradeMap);
                }
                qualMap.put("qualList", qualGradeList);
                qualList.add(qualMap);
            }
            qualCateMap.put("qualList", qualList);
            qualCateList.add(qualCateMap);
        }
        return qualCateList;
    }

    /**
     * 获取资质
     */
    public List<Map<String, Object>> getQua() {
        Map<String, Object> param = new HashMap<>();
        List<Map<String, Object>> list = dicQuaMapper.queryQuaOnes(param);
        List<Map<String, Object>> oneQuaListtMap = new ArrayList<>();
        //遍历资质一级
        for (Map<String, Object> map : list) {
            //把一级资质放入oneQuaMap中
            Map<String, Object> oneQuaMap = new HashMap<>();
            String one = (String) map.get("id");
            param.put("zzIdOne", one);
            param.put("noticeLevel", "2");
            List<Map<String, Object>> list1 = dicQuaMapper.queryQuaTwos(param);
            List<Map<String, Object>> towQuaListtMap = new ArrayList<>();
            for (Map<String, Object> map2 : list1) {
                Map<String, Object> towQuaMap = new HashMap<>();
                String tow = (String) map2.get("id");
                param.put("zzIdOne", tow);
                List<Map<String, Object>> threeQuaListtMap = new ArrayList<>();
                if (null == MapUtils.getString(map2, "benchName")) {
                    param.put("noticeLevel", "3");
                    List<Map<String, Object>> list2 = dicQuaMapper.queryQuaTwos(param);
                    for (Map<String, Object> map3 : list2) {
                        Map<String, Object> threeQuaMap = new HashMap<>();
                        String three = (String) map3.get("id");
                        param.put("zzIdOne", three);
                        param.put("noticeLevel", "4");
                        List<Map<String, Object>> fourQuaListtMap = new ArrayList<>();
                        if (null == MapUtils.getString(map3, "benchName")) {
                            List<Map<String, Object>> list3 = dicQuaMapper.queryQuaTwos(param);
                            for (Map<String, Object> map4 : list3) {
                                Map<String, Object> fourQuaMap = new HashMap<>();
                                String benchName = (String) map4.get("benchName");
                                if (StringUtils.isNotEmpty(benchName)) {
                                    fourQuaMap.put("quaCode", map4.get("quaCode"));
                                    fourQuaMap.put("id", map4.get("id"));
                                    fourQuaMap.put("name", map4.get("benchName"));
                                    fourQuaListtMap.add(fourQuaMap);
                                }
                            }
                        }
                        String benchName = (String) map3.get("benchName");
                        threeQuaMap.put("quaCode", map3.get("quaCode"));
                        threeQuaMap.put("id", map3.get("id"));
                        if (StringUtils.isNotEmpty(benchName)) {
                            threeQuaMap.put("name", map3.get("benchName"));
                        } else {
                            threeQuaMap.put("name", map3.get("quaName"));
                        }
                        if (null != fourQuaListtMap && fourQuaListtMap.size() >= 1) {
                            threeQuaMap.put("data", fourQuaListtMap);
                        }
                        threeQuaListtMap.add(threeQuaMap);
                    }
                }
                String benchName = (String) map2.get("benchName");
                towQuaMap.put("quaCode", map2.get("quaCode"));
                towQuaMap.put("id", map2.get("id"));
                if (StringUtils.isNotEmpty(benchName)) {
                    towQuaMap.put("name", map2.get("benchName"));
                } else {
                    towQuaMap.put("name", map2.get("quaName"));

                }
                if (null != threeQuaListtMap && threeQuaListtMap.size() >= 1) {
                    towQuaMap.put("data", threeQuaListtMap);
                }
                towQuaListtMap.add(towQuaMap);
            }
            oneQuaMap.put("quaCode", map.get("quaCode"));
            oneQuaMap.put("id", map.get("id"));
            oneQuaMap.put("name", map.get("quaName"));
            oneQuaMap.put("data", towQuaListtMap);
            oneQuaListtMap.add(oneQuaMap);
        }
        return oneQuaListtMap;
    }

    /**
     * 获取资质属性
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getBizType(Map<String, Object> param) {
        return dicQuaMapper.queryBizType(param);
    }

    /**
     * 修改资质属性
     *
     * @param param
     */
    @Override
    public void updateBizType(Map<String, Object> param) {
        dicQuaMapper.updateBizType(param);
    }

    public void getLevel(String levelType, Map<String, Object> param) {
        if (levelType.equals("0")) {
            relQuaGradeMapper.deleteRelQuaCode(param);//根据等级qua_code 删除资质管理表达式中的所有有关的数据
            param.put("gradeCode", "0");
            param.put("id", DataHandlingUtil.getUUID());
            param.put("bizType", "");
            relQuaGradeMapper.insertQuaCrade(param);
        } else {
            relQuaGradeMapper.deleteRelQuaCode(param);//根据等级qua_code 删除资质管理表达式中的所有有关的数据
            List<String> list = dicCommonMapper.queryLevelCode(param);
            for (String s : list) {
                param.put("id", DataHandlingUtil.getUUID());
                param.put("bizType", "");
                param.put("gradeCode", s);
                relQuaGradeMapper.insertQuaCrade(param);
            }
        }
    }

}
