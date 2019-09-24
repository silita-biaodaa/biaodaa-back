package com.silita.service;

import com.silita.model.RelQuaGrade;

import java.util.List;
import java.util.Map;

/**
 * 资质等级 Service
 */
public interface IRelQuaGradeService {

    /**
     * 添加资质等级
     * @param grade
     * @return
     */
   // Map<String,Object> addQuaGrade(RelQuaGrade grade);

    /**
     * 删除
     * @param param
     */
    void delQuaGrade(Map<String,Object> param);

    /**
     * 获取资质下的等级
     * @param param
     * @return
     */
    List<RelQuaGrade> getQualGradeList(Map<String,Object> param);

    /**
     * 修改资质名称维护-资质等级
     * @param param
     * @return
     */
    Map<String,Object> updateRelQuaGrade(Map<String,Object> param);
}
