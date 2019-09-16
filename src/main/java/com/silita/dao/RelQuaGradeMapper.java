package com.silita.dao;

import com.silita.model.RelQuaGrade;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * 资质等级 Mapper
 */
public interface RelQuaGradeMapper extends MyMapper<RelQuaGrade>{

    /**
     * 获取资质下的等级
     *
     * @param param
     * @return
     */
    List<RelQuaGrade> queryQuaGrade(Map<String, Object> param);

    /**
     * 添加资质等级
     *
     * @param quaGrade
     * @return
     */
    int insertQuaCrade(RelQuaGrade quaGrade);

    /**
     * 查询资质等级个数
     *
     * @param quaGrade
     * @return
     */
    Integer queryQuaGradeCout(RelQuaGrade quaGrade);

    /**
     * 删除资质等级
     *
     * @param param
     * @return
     */
    Integer delQuaCrade(Map<String,Object> param);

    /**
     * 获取等级个数
     * @param gradeCode
     * @return
     */
    Integer quaryGradeCountByCode(String gradeCode);


    /**
     * 通过子级code找到对应的等级code
     * @return
     */
    Integer queryRelQuaGrade(String quaCode);

    /**
     * 通过子级code找到对应的等级code
     * @return
     */
    List<Map<String,Object>> queryRelQuaGrades(String quaCode);
}