package com.silita.service;

import com.silita.model.RelQuaGrade;

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
    Map<String,Object> addQuaGrade(RelQuaGrade grade);

    /**
     * 删除
     * @param param
     */
    void delQuaGrade(Map<String,Object> param);
}
