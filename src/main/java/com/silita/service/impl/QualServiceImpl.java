package com.silita.service.impl;

import com.silita.common.BasePageModel;
import com.silita.common.Constant;
import com.silita.common.PageBean;
import com.silita.dao.DicAliasMapper;
import com.silita.dao.DicQuaMapper;
import com.silita.dao.RelQuaGradeMapper;
import com.silita.model.DicAlias;
import com.silita.model.DicQua;
import com.silita.model.RelQuaGrade;
import com.silita.service.IQualService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.stringUtils.PinYinUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

/**
 * 资质实现类
 */
@Service
public class QualServiceImpl implements IQualService {

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

    @Override
    public List<DicQua> getDicQuaList(Map<String, Object> param) {
        return dicQuaMapper.queryDicQuaList(param);
    }

    /**
     * 获取资质列表
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> getDicQuaListMap(Map<String, Object> param) {

        /**
         * 先判断是否是最后一级
         * 如果是旧直接往上查就是
         *
         */

        List<Map<String, Object>> dicQuaListMap = new ArrayList<>();

        param.put("noticeLevel", "4");
        List<Map<String, Object>> listone = dicQuaMapper.queryQuaTwo(param);
        for (Map<String, Object> map : listone) {
            String quaCode = (String) map.get("quaCode");
            Integer integer = relQuaGradeMapper.queryRelQuaGrade(quaCode);
            param.put("zid", map.get("id"));
            Integer integer1 = dicQuaMapper.queryQuaparentIdIsNull(param);
            if ((integer != 0 && integer > 0) || integer1 == 0) {
                param.put("parentId", map.get("parentId"));
                param.put("noticeLevel", "3");
                List<Map<String, Object>> list1 = dicQuaMapper.queryQuaparentId(param);
                for (Map<String, Object> map1 : list1) {
                    param.put("parentId", map1.get("parentId"));
                    param.put("noticeLevel", "2");
                    List<Map<String, Object>> list2 = dicQuaMapper.queryQuaparentId(param);
                    for (Map<String, Object> map2 : list2) {
                        param.put("parentId", map2.get("parentId"));
                        List<Map<String, Object>> list3 = dicQuaMapper.queryQuaOne(param);
                        for (Map<String, Object> map3 : list3) {
                            Map<String, Object> oneMap = new HashMap<>();
                            oneMap.put("quaName", map3.get("quaName"));
                            oneMap.put("id", map.get("id"));
                            oneMap.put("quaCode", map.get("quaCode"));
                            oneMap.put("benchName", map.get("benchName"));
                            dicQuaListMap.add(oneMap);
                        }
                    }
                }
            }
        }

        param.put("noticeLevel", "3");
        List<Map<String, Object>> listtwo = dicQuaMapper.queryQuaTwo(param);
        for (Map<String, Object> map : listtwo) {
            String quaCode = (String) map.get("quaCode");
            Integer integer = relQuaGradeMapper.queryRelQuaGrade(quaCode);
            param.put("zid", map.get("id"));
            Integer integer1 = dicQuaMapper.queryQuaparentIdIsNull(param);
            if ((integer != 0 && integer > 0) || integer1 == 0) {
                param.put("parentId", map.get("parentId"));
                param.put("noticeLevel", "2");
                List<Map<String, Object>> list1 = dicQuaMapper.queryQuaparentId(param);
                for (Map<String, Object> map1 : list1) {
                    param.put("parentId", map1.get("parentId"));
                    List<Map<String, Object>> list3 = dicQuaMapper.queryQuaOne(param);
                    for (Map<String, Object> map2 : list3) {
                        Map<String, Object> oneMap = new HashMap<>();
                        oneMap.put("quaName", map2.get("quaName"));
                        oneMap.put("id", map.get("id"));
                        oneMap.put("quaCode", map.get("quaCode"));
                        oneMap.put("benchName", map.get("benchName"));
                        dicQuaListMap.add(oneMap);
                    }
                }
            }
        }

        param.put("noticeLevel", "2");
        List<Map<String, Object>> listthree = dicQuaMapper.queryQuaTwo(param);
        for (Map<String, Object> map : listthree) {
            String quaCode = (String) map.get("quaCode");
            Integer integer = relQuaGradeMapper.queryRelQuaGrade(quaCode);
            param.put("zid", map.get("id"));
            Integer integer1 = dicQuaMapper.queryQuaparentIdIsNull(param);
            if ((integer != 0 && integer > 0) || integer1 == 0) {
                param.put("parentId", map.get("parentId"));
                List<Map<String, Object>> list3 = dicQuaMapper.queryQuaOne(param);
                for (Map<String, Object> map1 : list3) {
                    Map<String, Object> oneMap = new HashMap<>();
                    oneMap.put("quaName", map1.get("quaName"));
                    oneMap.put("id", map.get("id"));
                    oneMap.put("quaCode", map.get("quaCode"));
                    oneMap.put("benchName", map.get("benchName"));
                    dicQuaListMap.add(oneMap);
                }
            }
        }


        return dicQuaListMap;
    }



    @Override
    public List<Map<String,Object>> getDicQuaListMaps(Map<String, Object> param) {
        List<Map<String, Object>> dicQuaListMap = new ArrayList<>();

        List<Map<String, Object>> list = dicQuaMapper.queryDicQuaBenchNameListMap(param);
        for (Map<String, Object> map : list) {

            String parentId = (String) map.get("parentId");
            if (StringUtil.isNotEmpty(parentId)) {
                param.put("parentId", parentId);
                List<Map<String, Object>> list1 = dicQuaMapper.queryDicQuaBenchNameListMap(param);
                for (Map<String, Object> map1 : list1) {
                    String parentId1 = (String) map1.get("parentId");
                    if (StringUtil.isNotEmpty(parentId1)) {
                        param.put("parentId", parentId1);
                        List<Map<String, Object>> list2 = dicQuaMapper.queryDicQuaBenchNameListMap(param);
                        for (Map<String, Object> map2 : list2) {
                            String parentId2 = (String) map2.get("parentId");
                            if (StringUtil.isNotEmpty(parentId2)) {
                                param.put("parentId", parentId2);
                                List<Map<String, Object>> list3 = dicQuaMapper.queryDicQuaBenchNameListMap(param);
                                for (Map<String, Object> map3 : list3) {
                                    String benchName = (String) map.get("benchName");
                                    if (StringUtil.isNotEmpty(benchName)) {
                                        Map<String, Object> threeMap = new HashMap<>();
                                        threeMap.put("quaName", map3.get("quaName"));
                                        threeMap.put("id", map.get("id"));
                                        threeMap.put("code", map.get("quaCode"));
                                        threeMap.put("benchName", map.get("benchName"));
                                        dicQuaListMap.add(threeMap);
                                    }
                                }
                            } else {
                                String benchName = (String) map.get("benchName");
                                if (StringUtil.isNotEmpty(benchName)) {
                                    Map<String, Object> towMap = new HashMap<>();
                                    towMap.put("quaName", map2.get("quaName"));
                                    towMap.put("id", map.get("id"));
                                    towMap.put("code", map.get("quaCode"));
                                    towMap.put("benchName", map.get("benchName"));
                                    dicQuaListMap.add(towMap);
                                }
                            }
                        }
                    } else {
                        String benchName = (String) map.get("benchName");
                        if (StringUtil.isNotEmpty(benchName)) {
                            Map<String, Object> oneMap = new HashMap<>();
                            oneMap.put("quaName", map1.get("quaName"));
                            oneMap.put("id", map.get("id"));
                            oneMap.put("code", map.get("quaCode"));
                            oneMap.put("benchName", map.get("benchName"));
                            dicQuaListMap.add(oneMap);
                        }
                    }
                }
            }
        }

        //再对集合进行分页
        BasePageModel basePageModel = new BasePageModel();
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        basePageModel.setPage(pageNo);//起始页是第一页
        basePageModel.setRows(pageSize);//一页5行
        List<Map<String,Object>> pageList = PageBean.getPageListMap(dicQuaListMap,basePageModel);


        return pageList;
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
}
