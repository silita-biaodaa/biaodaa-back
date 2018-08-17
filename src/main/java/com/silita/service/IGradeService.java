package com.silita.service;

import com.silita.model.DicAlias;
import com.silita.model.DicCommon;

import java.util.List;
import java.util.Map;

/**
 * 等级 service
 */
public interface IGradeService {

    /**
     * 等级
     * @param param
     * @return
     */
    List<Map<String, Object>> getGradeList(Map<String, Object> param);

    /**
     * 保存
     * @param dicCommon
     * @param username
     */
    Map<String,Object> saveGrade(DicCommon dicCommon,String username);

    /**
     * 删除等级
     * @param param
     */
    void delGrade(Map<String,Object> param);

    /**
     * 添加等级别名
     * @param alias
     */
    Map<String,Object> addGradeAlias(DicAlias alias);

    /**
     * 资质等级
     * @param param
     * @return
     */
    List<Map<String, Object>> getQualGradeList(Map<String, Object> param);

    /**
     * 资质二等级
     * @param param
     * @return
     */
    List<Map<String, Object>> getSecQualGradeList(Map<String,Object> param);

    /**
     * 修改等级别名
     * @param dicAlias
     */
    Map<String,Object> updateGradeAlias(DicAlias dicAlias);
}
