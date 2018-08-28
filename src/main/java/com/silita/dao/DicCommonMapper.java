package com.silita.dao;

import com.silita.model.DicCommon;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface DicCommonMapper extends MyMapper<DicCommon> {

    /**
     * 添加公共数据词典表数据
     * @param dicCommon
     */
    void insertDicCommon(DicCommon dicCommon);

    /**
     * 根据id更新公共数据词典表数据
     * @param dicCommon
     */
    void updateDicCommonById(DicCommon dicCommon);

    /**
     * 根据type获取公共数据词典表数据（）
     * @param dicCommon
     * @return
     */
    List<DicCommon> listDicCommonByType(DicCommon dicCommon);

    /**
     * 根据type获取公共数据词典表数据条数
     * @param dicCommon
     * @return
     */
    Integer getDicCommonCountByType(DicCommon dicCommon);

    /**
     * 根据id删除公共数据词典表数据
     * @param ids
     */
    void deleteDicCommonByIds(Object[] ids);

    /**
     * 获取资质父类等级
     * @return
     */
    List<Map<String,Object>> queryParentGrade();

    /**
     * 获取所有等级
     * @param param
     * @return
     */
    List<Map<String,Object>> queryGradeList(Map<String,Object> param);

    /**
     * 查询名称个数
     * @param param
     * @return
     */
    Integer queryDicCommCountByName(Map<String,Object> param);

    /**
     * 根据id获取公共数据词典表数据
     * @param ids
     * @return
     */
    List<DicCommon> listDicCommonByIds(Object[] ids);

    /**
     * 根据type获取公共数据词典表name字段（）
     * @param dicCommon
     * @return
     */
    List<String> listDicCommonNameByType(DicCommon dicCommon);

}