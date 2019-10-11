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
     * @param param
     * @return
     */
    void insertQuaCrade(Map<String,Object> param);

    /**
     * 批量添加资质等级
     * @param param
     */
    void insertRelQuaGrade(Map<String,Object> param);

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
     * 删除资质关系表达式
     * @param param
     */
    void deleteIsNotLevel(Map<String,Object> param);

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

    /**
     * 根据等级grade_code 删除资质管理表达式中的所有有关的数据
     * @param param
     */
    void deleteRelQuaGrade(Map<String,Object> param);

    /**
     * 根据等级qua_code 删除资质管理表达式中的所有有关的数据
     * @param param
     */
    void deleteRelQuaCode(Map<String,Object> param);

    /**
     * 修改资质关系表达式的quaCode
     * @param param
     */
    void updateQualCode(Map<String,Object> param);

    /**
     * 判断资质关系表达式中该资质是否拥有该等级
     * @param param
     * @return
     */
    Integer queryQualLevelBoolean(Map<String,Object> param);

    /**
     * 获取资质关系表达式id
     * @param param
     * @return
     */
    List<String> queryRelId(Map<String,Object> param);

    /**
     * 根据资质qua_code统计数量
     * @return
     */
    Integer queryRelQuaGradeCount(Map<String,Object> param);


    /**
     * 通过子级code找到对应的等级code 用于redis缓存
     * @return
     */
    List<Map<String,Object>> queryRelQuaGradeRedis(String quaCode);


}