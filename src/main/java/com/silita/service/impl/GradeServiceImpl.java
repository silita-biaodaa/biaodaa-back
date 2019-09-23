package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.DicAliasMapper;
import com.silita.dao.DicCommonMapper;
import com.silita.dao.RelQuaGradeMapper;
import com.silita.model.DicAlias;
import com.silita.model.DicCommon;
import com.silita.model.RelQuaGrade;
import com.silita.service.IGradeService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.stringUtils.PinYinUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

@Service
public class GradeServiceImpl extends AbstractService implements IGradeService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(GradeServiceImpl.class);

    @Autowired
    DicCommonMapper dicCommonMapper;
    @Autowired
    DicAliasMapper dicAliasMapper;
    @Autowired
    RelQuaGradeMapper relQuaGradeMapper;

    @Override
    public List<Map<String, Object>> getGradeList(Map<String, Object> param) {
        //TODO
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

    /**
     * 等级列表
     *
     * @param relQuaGrade
     * @return
     */
    @Override
    public Map<String, Object> getDicCommonGradeList(RelQuaGrade relQuaGrade) {

        Map<String, Object> params = new HashMap<>();
        params.put("list", dicCommonMapper.queryDicCommonGradeList(relQuaGrade));
        params.put("total", dicCommonMapper.queryDicCommonGradeListCount(relQuaGrade));

        return super.handlePageCount(params, relQuaGrade);
    }

    @Override
    public Map<String, Object> saveGrade(DicCommon dicCommon, String username) {
        Integer count = 0;
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("name", dicCommon.getName());
        param.put("type", Constant.TYPE_QUA_GRADE);
        if (null != dicCommon.getId()) {
            param.put("id", dicCommon.getId());
            count = dicCommonMapper.queryDicCommCountByName(param);
            if (count > 0) {
                resultMap.put("code", Constant.CODE_WARN_400);
                resultMap.put("msg", Constant.MSG_WARN_400);
                return resultMap;
            }
            dicCommon.setUpdateBy(username);
            dicCommonMapper.updateDicCommonById(dicCommon);
        } else {
            count = dicCommonMapper.queryDicCommCountByName(param);
            if (count > 0) {
                resultMap.put("code", Constant.CODE_WARN_400);
                resultMap.put("msg", Constant.MSG_WARN_400);
                return resultMap;
            }
            dicCommon.setId(DataHandlingUtil.getUUID());
            dicCommon.setCode("grade_" + PinYinUtil.cn2py(dicCommon.getName()) + "_" + +System.currentTimeMillis());
            dicCommon.setType(Constant.TYPE_QUA_GRADE);
            dicCommon.setCreateBy(username);
            dicCommonMapper.insertDicCommon(dicCommon);
        }
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    /**
     * 删除等级
     *
     * @param param
     */
    @Override
    public Map<String, Object> delGrade(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        String id = MapUtils.getString(param, "id");
        List<DicCommon> dicCommons = dicCommonMapper.listDicCommonByIds(id.split(","));
        DicCommon dic = dicCommons.get(0);
        Integer count = relQuaGradeMapper.quaryGradeCountByCode(dic.getCode());
        if (count > 0) {
            resultMap.put("code", Constant.CODE_WARN_407);
            resultMap.put("msg", Constant.MSG_WARN_407);
            return resultMap;
        }
        dicCommonMapper.deleteDicCommonByIds(id.split(","));
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    /**
     * 添加等级
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> insertGradeLevel(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            param.put("id", DataHandlingUtil.getUUID());
            param.put("type", "qua_grade");//等级类型
            String name = MapUtils.getString(param, "name");
            param.put("code", "grade_" + PinYinUtil.cn2py(name) + "_" + System.currentTimeMillis());
            Integer integer = dicCommonMapper.queryDicCommonName(param);
            if (null != integer && integer != 0) { // 判断名称是否存在
                resultMap.put("code", "0");
                resultMap.put("msg", "等级名称已存在");
                return resultMap;
            }
            Integer orderNo = dicCommonMapper.queryMaxOrderNo(param);
            param.put("orderNo", orderNo + 1);//排序
            dicCommonMapper.insertGradeLevel(param);

            Integer maxOrderNo = dicCommonMapper.queryMaxOrderNo(param);//最大等级
            if (null == maxOrderNo) {
                maxOrderNo = 0;
            }
            Integer minOredrNo = dicCommonMapper.queryMinOredrNo(param);//最小等级
            if (null == minOredrNo) {
                minOredrNo = 0;
            }
            if (maxOrderNo >= minOredrNo) {
                String names = name + "及以上";
                param.put("name", names);
                Integer integer1 = dicCommonMapper.queryDicCommonName(param);
                if (null == integer1 || integer == 0) {
                    param.put("id", DataHandlingUtil.getUUID());
                    param.put("code", "grade_" + PinYinUtil.cn2py(names) + "_" + System.currentTimeMillis());
                    param.put("orderNo", maxOrderNo + 1);
                    dicCommonMapper.insertGradeLevel(param);
                    resultMap.put("code", Constant.CODE_SUCCESS);
                    resultMap.put("msg", Constant.MSG_SUCCESS);
                    return resultMap;
                }
            }
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
        } catch (Exception e) {
            logger.error("添加等级", e);
        }
        return resultMap;
    }

    /**
     * 更新等级
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> updateGradeLevel(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            param.put("type", "qua_grade");//等级类型
            String name = MapUtils.getString(param, "name");
            param.put("code", "grade_" + PinYinUtil.cn2py(name) + "_" + System.currentTimeMillis());
            Integer integer = dicCommonMapper.queryDicCommonName(param);
            if (null != integer && integer != 0) { // 判断名称是否存在
                resultMap.put("code", "0");
                resultMap.put("msg", "等级名称已存在");
                return resultMap;
            }
            String commonName = dicCommonMapper.getCommonNameId(param);
            dicCommonMapper.updateGradeLevel(param);
            String names = commonName + "及以上";
            param.put("names", names);
            String commonIdName = dicCommonMapper.getCommonIdName(param);
            if (StringUtil.isNotEmpty(commonIdName)) {
                param.put("id", commonIdName);
                String name2 = name + "及以上";
                param.put("name", name2);
                param.put("code", "grade_" + PinYinUtil.cn2py(name2) + "_" + System.currentTimeMillis());
                dicCommonMapper.updateGradeLevel(param);
            }
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
        } catch (Exception e) {
            logger.error("更新等级", e);
        }
        return resultMap;
    }

    /**
     * 删除等级
     */
    @Override
    public void deleteGradeLevel(Map<String, Object> param) {
        try {
            String commonName = dicCommonMapper.getCommonNameId(param);//获取等级名称
            String names = commonName + "及以上";
            param.put("names", names);
            String commonId = dicCommonMapper.getCommonIdName(param);//获取等级id
            String code = dicCommonMapper.queryDicCommonCode(param);//获取等级code
            if (StringUtil.isNotEmpty(code)) {
                param.put("stdCode", code);
                dicAliasMapper.deleteAilas(param);//删除别名
                relQuaGradeMapper.deleteRelQuaGrade(param);//删除资质管理表达式
            }
            Integer integer = dicCommonMapper.queryMinOredrNo(param);
            Integer integer1 = dicCommonMapper.queryOrderNo(param);
            if(integer == integer1){
                dicCommonMapper.deleteGradeLevel(param);//删除等级
                Integer orderNo = dicCommonMapper.queryMinOredrNo(param);
                param.put("orderNo",orderNo);
                String commonNameOrderNo = dicCommonMapper.getCommonNameOrderNo(param);//获取name
                String name = commonNameOrderNo+"及以上";
                param.put("names",name);
                String commonId2 = dicCommonMapper.getCommonIdName(param);
                if(StringUtil.isNotEmpty(commonId2)){
                    param.put("id",commonId2);
                    String code2 = dicCommonMapper.queryDicCommonCode(param);
                    param.put("stdCode", code2);
                    relQuaGradeMapper.deleteRelQuaGrade(param);//删除资质管理表达式
                    dicAliasMapper.deleteAilas(param);//删除别名
                    dicCommonMapper.deleteGradeLevel(param);//删除等级
                }
            }else{
                dicCommonMapper.deleteGradeLevel(param);//删除等级
            }
            if (StringUtil.isNotEmpty(commonId)) {
                param.put("id", commonId);
                String code2 = dicCommonMapper.queryDicCommonCode(param);
                param.put("stdCode", code2);
                dicAliasMapper.deleteAilas(param);//删除别名
                relQuaGradeMapper.deleteRelQuaGrade(param);//删除资质管理表达式
                dicCommonMapper.deleteGradeLevel(param);//删除等级
            }
        }catch (Exception e){
            logger.error("删除等级",e);
        }
    }

    /**
     * 添加等级别名
     *
     * @param alias
     */
    @Override
    public Map<String, Object> addGradeAlias(DicAlias alias) {
        Map<String, Object> resultMap = new HashMap<>();
        //判断名称是否存在
        Map<String, Object> param = new HashMap<>();
        param.put("name", alias.getName());
        param.put("stdType", Constant.GRADE_STD_TYPE);
        Integer count = dicAliasMapper.queryAliasByName(param);
        if (count > 0) {
            resultMap.put("code", Constant.CODE_WARN_400);
            resultMap.put("msg", Constant.MSG_WARN_400);
            return resultMap;
        }
        alias.setId(DataHandlingUtil.getUUID());
        String code = "alias_grade" + PinYinUtil.cn2py(alias.getName()) + "_" + System.currentTimeMillis();
        alias.setCode(code);
        alias.setStdType(Constant.GRADE_STD_TYPE);
        dicAliasMapper.insertDicAlias(alias);
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }


   /* @Override
    public List<Map<String, Object>> getQualGradeList(Map<String, Object> param) {
        //TODO:
        List<Map<String, Object>> parentList = dicCommonMapper.queryParentGrade();
        return parentList;
    }*/

    /**
     * 等级下拉选项
     * 要求：该资质没有的等级
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> getQualGradeList(Map<String, Object> param) {
        //TODO:
        List<Map<String, Object>> parentList = dicCommonMapper.queryParentGrade();
        return parentList;
    }

    @Override
    public List<Map<String, Object>> getSecQualGradeList(Map<String, Object> param) {
        List<Map<String, Object>> gradeList = dicCommonMapper.queryGradeList(param);
        return gradeList;
    }

    /**
     * 获取符合该资质的等级
     *
     * @param relQuaGrade
     * @return
     */
    @Override
    public Map<String, Object> getGradeListMap(RelQuaGrade relQuaGrade) {
        Map<String, Object> params = new HashMap<>();
        params.put("list", dicCommonMapper.queryGradeListMap(relQuaGrade));
        params.put("total", dicCommonMapper.queryGradeListMapCount(relQuaGrade));
        return super.handlePageCount(params, relQuaGrade);
    }

    /**
     * 资质等级下拉选项
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> gitGradePullDownListMap(Map<String, Object> param) {
        RelQuaGrade relQuaGrade = new RelQuaGrade();
        relQuaGrade.setQuaCode(MapUtils.getString(param, "quaCode"));

        List<Map<String, Object>> gradeListMap = dicCommonMapper.queryGradeListMapPullDown(relQuaGrade);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (Map<String, Object> map : gradeListMap) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("code", map.get("code"));
            listMap.add(map1);
            param.put("parentId", map.get("parentId"));
        }
        param.put("listMapCode", listMap);
        List<Map<String, Object>> list1 = dicCommonMapper.queryGradePullDownListMap(param);
        return list1;
    }


    @Override
    public Map<String, Object> updateGradeAlias(DicAlias dicAlias) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("id", dicAlias.getId());
        param.put("name", dicAlias.getName());
        param.put("stdType", Constant.GRADE_STD_TYPE);
        Integer count = dicAliasMapper.queryAliasByName(param);
        if (count > 0) {
            resultMap.put("code", Constant.CODE_WARN_400);
            resultMap.put("msg", Constant.MSG_WARN_400);
            return resultMap;
        }
        String code = "alias_grade" + PinYinUtil.cn2py(dicAlias.getName()) + "_" + System.currentTimeMillis();
        dicAlias.setCode(code);
        dicAliasMapper.updateDicAliasById(dicAlias);
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }
}
