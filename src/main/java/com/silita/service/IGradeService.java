package com.silita.service;

import com.silita.model.DicAlias;
import com.silita.model.DicCommon;
import com.silita.model.RelQuaGrade;

import java.util.List;
import java.util.Map;

/**
 * 等级 service
 */
public interface IGradeService {

    /**
     * 等级
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> getGradeList(Map<String, Object> param);

    /**
     * 等级
     * @param relQuaGrade
     * @return
     */
    Map<String,Object> getDicCommonGradeList(RelQuaGrade relQuaGrade);

    /**
     * 保存
     *
     * @param dicCommon
     * @param username
     */
    Map<String, Object> saveGrade(DicCommon dicCommon, String username);

    /**
     * 删除等级
     *
     * @param param
     */
    Map<String, Object> delGrade(Map<String, Object> param);

    /**
     * 添加等级
     * @param param
     * @return
     */
    Map<String,Object> insertGradeLevel(Map<String,Object> param);

    /**
     * 更新等级
     * @param param
     * @return
     */
    Map<String,Object> updateGradeLevel(Map<String,Object> param);


    /**
     * 删除等级
     * @param param
     */

    void deleteGradeLevel(Map<String,Object> param);


    /**
     * 添加等级别名
     *
     * @param alias
     */
    Map<String, Object> addGradeAlias(DicAlias alias);

    /**
     * 资质等级
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> getQualGradeList(Map<String, Object> param);

    /**
     * 资质二等级
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> getSecQualGradeList(Map<String, Object> param);

    /**
     * 获取符合该资质的等级
     * @param param
     * @return
     */
    List<Map<String,Object>> getGradeListMap(Map<String,Object> param);

    /**
     * 资质等级下拉选项
     * @param param
     * @return
     */
    List<Map<String,Object>> gitGradePullDownListMap(Map<String,Object> param);

    /**
     * 修改等级别名
     *
     * @param dicAlias
     */
    Map<String, Object> updateGradeAlias(DicAlias dicAlias);
}
