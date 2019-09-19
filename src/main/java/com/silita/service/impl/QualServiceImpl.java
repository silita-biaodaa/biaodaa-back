package com.silita.service.impl;

import com.silita.common.BasePageModel;
import com.silita.common.Constant;
import com.silita.dao.DicAliasMapper;
import com.silita.dao.DicQuaMapper;
import com.silita.dao.RelQuaGradeMapper;
import com.silita.model.DicAlias;
import com.silita.model.DicQua;
import com.silita.model.RelQuaGrade;
import com.silita.service.IQualService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.stringUtils.PinYinUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
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
        param.put("parentId", qua.getParentId());
        if (null == qua.getLevel()) {
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
            if (null == qua.getBizType()) {
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
        if(split.length >= 1){
            param.put("id",split[0]);
        }
        if(split.length >= 2){
            param.put("two",split[1]);
        }
        if(split.length >= 3){
            param.put("three",split[2]);
        }
        if(split.length >= 4){
            param.put("four",split[3]);
        }


        String benchName1 = MapUtils.getString(param, "benchName");
        Map<String,Object> result = new HashMap<>();
        if(StringUtil.isNotEmpty(benchName1)){
            List<String> list = dicQuaMapper.queryBenchNames(param);
            for (String s : list) {
                result.put(s,"0");
            }
        }


        //param.put("noticeLevel","1");
        param.put("zzIdOne","");
        List<Map<String, Object>> list = dicQuaMapper.queryQuaOne(param);
        List<Map<String, Object>> oneQuaListMap = new ArrayList<>();
        //遍历资质一级
        for (Map<String, Object> map : list) {
            //把一级资质放入oneQuaMap中
            String one = (String) map.get("id");
            param.put("zzIdOne", one);
            //param.put("noticeLevel", "2");
            List<Map<String, Object>> list1 = dicQuaMapper.queryQuaTwo(param);
            for (Map<String, Object> map2 : list1) {
                Map<String, Object> towQuaMap = new HashMap<>();
                String tow = (String) map2.get("id");
                param.put("zzIdOne", tow);
                if (null == MapUtils.getString(map2, "benchName")) {
                    //param.put("noticeLevel", "3");
                    List<Map<String, Object>> list2 = dicQuaMapper.queryQuaThree(param);
                    for (Map<String, Object> map3 : list2) {
                        Map<String, Object> threeQuaMap = new HashMap<>();
                        String three = (String) map3.get("id");
                        param.put("zzIdOne", three);
                        //param.put("noticeLevel", "4");
                        if (null == MapUtils.getString(map3, "benchName")) {
                            List<Map<String, Object>> list3 = dicQuaMapper.queryQuaFout(param);
                            for (Map<String, Object> map4 : list3) {
                                Map<String, Object> fourQuaMap = new HashMap<>();
                                String benchName = (String) map4.get("benchName");
                                if(StringUtil.isEmpty(benchName1)){
                                    if (StringUtils.isNotEmpty(benchName)) {
                                        fourQuaMap.put("quaName", map.get("quaName"));
                                        fourQuaMap.put("quaBig", map2.get("quaName"));
                                        fourQuaMap.put("quaTiny", map3.get("quaName"));
                                        fourQuaMap.put("id", map4.get("id"));
                                        fourQuaMap.put("quaCode", map4.get("quaCode"));
                                        fourQuaMap.put("benchName", map4.get("benchName"));
                                        oneQuaListMap.add(fourQuaMap);
                                    }
                                }else{
                                    String benchName2 = MapUtils.getString(result, MapUtils.getString(map4, "benchName"));
                                    if(StringUtil.isNotEmpty(benchName2)){
                                        if (StringUtils.isNotEmpty(benchName)) {
                                            fourQuaMap.put("quaName", map.get("quaName"));
                                            fourQuaMap.put("quaBig", map2.get("quaName"));
                                            fourQuaMap.put("quaTiny", map3.get("quaName"));
                                            fourQuaMap.put("id", map4.get("id"));
                                            fourQuaMap.put("benchName", map4.get("benchName"));
                                            fourQuaMap.put("quaCode", map4.get("quaCode"));
                                            oneQuaListMap.add(fourQuaMap);
                                        }
                                    }
                                }
                            }
                        }
                        String benchName = (String) map3.get("benchName");
                        if(StringUtil.isEmpty(benchName1)) {
                            if (StringUtil.isNotEmpty(benchName)) {
                                threeQuaMap.put("quaName", map.get("quaName"));
                                threeQuaMap.put("quaBig", map2.get("quaName"));
                                threeQuaMap.put("quaTiny", "");
                                threeQuaMap.put("quaCode", map3.get("quaCode"));
                                threeQuaMap.put("id", map3.get("id"));
                                threeQuaMap.put("benchName", map3.get("benchName"));
                                oneQuaListMap.add(threeQuaMap);
                            }
                        }else{
                            String benchName2 = MapUtils.getString(result, MapUtils.getString(map3, "benchName"));
                            if(StringUtil.isNotEmpty(benchName2)){
                                if (StringUtils.isNotEmpty(benchName)) {
                                    threeQuaMap.put("quaName", map.get("quaName"));
                                    threeQuaMap.put("quaBig", map2.get("quaName"));
                                    threeQuaMap.put("quaTiny", "");
                                    threeQuaMap.put("quaCode", map3.get("quaCode"));
                                    threeQuaMap.put("benchName", map3.get("benchName"));
                                    threeQuaMap.put("id", map3.get("id"));
                                    oneQuaListMap.add(threeQuaMap);
                                }
                            }
                        }
                    }
                }
                String benchName = (String) map2.get("benchName");
                if(StringUtil.isEmpty(benchName1)) {
                    if (StringUtil.isNotEmpty(benchName)) {
                        towQuaMap.put("quaName", map.get("quaName"));
                        towQuaMap.put("quaBig", "");
                        towQuaMap.put("quaTiny", "");
                        towQuaMap.put("quaCode", map2.get("quaCode"));
                        towQuaMap.put("id", map2.get("id"));
                        towQuaMap.put("benchName", map2.get("benchName"));
                        oneQuaListMap.add(towQuaMap);
                    }
                }else{
                    String benchName2 = MapUtils.getString(result, MapUtils.getString(map2, "benchName"));
                    if(StringUtil.isNotEmpty(benchName2)){
                        if (StringUtils.isNotEmpty(benchName)) {
                            towQuaMap.put("quaName", map.get("quaName"));
                            towQuaMap.put("quaBig", "");
                            towQuaMap.put("quaTiny", "");
                            towQuaMap.put("quaCode", map2.get("quaCode"));
                            towQuaMap.put("benchName", map2.get("benchName"));
                            towQuaMap.put("id", map2.get("id"));
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
                List<RelQuaGrade> relQuaGrades = quaGradeMapper.queryQuaGrade(gradeMap);
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
     * @param param
     * @return
     */
    @Override
    public Map<String,Object> getBizType(Map<String, Object> param) {
       return dicQuaMapper.queryBizType(param);
    }

    /**
     * 修改资质属性
     * @param param
     */
    @Override
    public void updateBizType(Map<String, Object> param) {
        dicQuaMapper.updateBizType(param);
    }


}
