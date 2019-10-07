package com.silita.dao;

import com.silita.model.DicCommon;
import com.silita.model.RelQuaGrade;
import com.silita.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DicCommonMapper extends MyMapper<DicCommon> {

    /**
     * 添加公共数据词典表数据
     *
     * @param dicCommon
     */
    void insertDicCommon(DicCommon dicCommon);

    /**
     * 根据id更新公共数据词典表数据
     *
     * @param dicCommon
     */
    void updateDicCommonById(DicCommon dicCommon);

    /**
     * 根据type获取公共数据词典表数据（）
     *
     * @param dicCommon
     * @return
     */
    List<DicCommon> listDicCommonByType(DicCommon dicCommon);

    /**
     * 根据type获取公共数据词典表数据条数
     *
     * @param dicCommon
     * @return
     */
    Integer getDicCommonCountByType(DicCommon dicCommon);

    /**
     * 根据id删除公共数据词典表数据
     *
     * @param ids
     */
    void deleteDicCommonByIds(Object[] ids);

    /**
     * 获取资质父类等级
     *
     * @return
     */
    List<Map<String, Object>> queryParentGrade();

    //List<Map<String,Object>> queryParentGrade();

    /**
     * 获取所有等级
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> queryGradeList(Map<String, Object> param);

    /**
     * 获取符合该资质的等级
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> queryGradeListMap(Map<String,Object> param);
    /**
     * 获取符合该资质的等级
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> queryGradeListMaps(Map<String,Object> param);




    //统计资质等级
    Integer queryGradeListMapCount(RelQuaGrade relQuaGrade);

    /**
     * 等级维护 等级列表
     *
     * @param relQuaGrade
     * @return
     */
    List<Map<String, Object>> queryDicCommonGradeList(RelQuaGrade relQuaGrade);

    /**
     * 统计等级列表
     *
     * @return
     */
    Integer queryDicCommonGradeListCount(RelQuaGrade relQuaGrade);



    //获取资质等级类别
    String queryParentId(Map<String,Object> param);

    /**
     * 资质等级下拉选项
     *
     * @param param
     */
    List<Map<String, Object>> queryGradePullDownListMap(Map<String, Object> param);


    /**
     * 查询名称个数
     *
     * @param param
     * @return
     */
    Integer queryDicCommCountByName(Map<String, Object> param);

    /**
     * 根据id获取公共数据词典表数据
     *
     * @param ids
     * @return
     */
    List<DicCommon> listDicCommonByIds(Object[] ids);

    /**
     * 根据type获取公共数据词典表name字段（）
     *
     * @param dicCommon
     * @return
     */
    List<Map<String, Object>> listDicCommonNameByType(DicCommon dicCommon);

    /**
     * 根据code查询词典信息
     *
     * @param code
     * @return
     */
    DicCommon queryDicComm(String code);

    /**
     * 根据code获取name
     *
     * @param code
     * @return
     */
    String getNameByCode(String code);

    /**
     * 根据name获取code
     * @param param
     * @return
     */
    String getCodeByName(Map<String,Object> param);

    /**
     * 根据id获取别名name
     *
     * @param id
     * @return
     */
    String getCommonNameById(@Param("id") String id);

    /**
     * 根据id获取别名name
     * @param param
     * @return
     */
    String getCommonNameId(Map<String,Object> param);

    /**
     * 根据name获取id
     * @param param
     * @return
     */
    String getCommonIdName(Map<String,Object> param);

    /**
     * 根据order_no 获取 name
     * @param param
     * @return
     */
    String getCommonNameOrderNo(Map<String,Object> param);

    /**
     * 根据名称查询资质信息
     *
     * @param name
     * @return
     */
    DicCommon queryQuaGrade(String name);

    /**
     * 获取评标办法名称
     *
     * @param
     * @return
     */
    List<Map<String, Object>> queryPbMode();

    /**
     * 获取评标办法列表
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> queryList(Map<String, Object> param);

    /**
     * 批量删除评标办法
     *
     * @param param
     */
    void deleteDicCommonIds(Map<String, Object> param);

    /**
     * 删除评标办法
     * @param param
     */
    void deleteGradeLevel(Map<String,Object> param);

    /**
     * 根据id获取code
     * @param param
     * @return
     */
    String queryDicCommonCode(Map<String,Object> param);

    /**
     * 修改名称
     * @param param
     */
    void updateDicCommonId(Map<String,Object> param);

    /**
     * 根据名称判断该名称是否存在
     * @param param
     * @return
     */
    Integer queryDicCommonName(Map<String,Object> param);

    /**
     * 添加等级
     * @param param
     */
    void insertGradeLevel(Map<String,Object> param);

    /**
     * 更新等级
     * @param param
     */
    void updateGradeLevel(Map<String,Object> param);

    /**
     * 获取最大的order_no
     * @param param
     * @return
     */
    Integer queryMaxOrderNo(Map<String,Object> param);

    /**
     * 获取最小order_no
     * @param param
     * @return
     */
    Integer queryMinOredrNo(Map<String,Object> param);

    /**
     * 通过id 获取order_no
     * @param param
     * @return
     */
    Integer queryOrderNo(Map<String,Object> param);

    /**
     * 根据等级类别获取等级code
     * @param param
     * @return
     */
    List<String> queryLevelCode(Map<String,Object> param);



}