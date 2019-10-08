package com.silita.dao;

import com.silita.model.DicQuaAnalysis;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface DicQuaAnalysisMapper extends MyMapper<DicQuaAnalysis> {
    /**
     * 资质解析列表
     * @param dicQuaAnalysis
     * @return
     */
    List<Map<String,Object>> queryQuaAnalysisList(DicQuaAnalysis dicQuaAnalysis);

    /**
     * 资质解析列表统计
     * @param dicQuaAnalysis
     * @return
     */
    Integer queryQuaAnalysisListCount(DicQuaAnalysis dicQuaAnalysis);

    /**
     * 添加资质解析组合数据
     * @param param
     */
    void insertAanlysisOne(Map<String,Object> param);
    /**
     * 添加资质解析组合数据
     * @param param
     */
    void insertAanlysis(Map<String,Object> param);

    /**
     * 删除资质解析组合数据
     * @param param
     */
    void deleteAanlysis(Map<String,Object> param);
    /**
     * 删除资质解析组合数据
     * @param param
     */
    void deleteAanlysisRelId(Map<String,Object> param);
    /**
     * 根据id获取资质关系表达式id
     */
    String queryRelId(Map<String,Object> param);

    /**
     * 删除资质解析组合数据
     * @param param
     */
    void deleteAilasId(Map<String,Object> param);

    /**
     * 删除资质解析组合数据
     * @param param
     */
    void deleteLevelAilasId(Map<String,Object> param);

    /**
     * 判断组合别名是否存在
     * @param param
     * @return
     */
    Integer queryJointAilas(Map<String,Object> param);
    /**
     * 判断组合别名是否存在
     * @param param
     * @return
     */
    Integer queryQuaLevel(Map<String,Object> param);


}