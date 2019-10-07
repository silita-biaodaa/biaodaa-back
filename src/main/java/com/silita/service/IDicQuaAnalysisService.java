package com.silita.service;

import com.silita.model.DicQuaAnalysis;

import java.util.Map;

public interface IDicQuaAnalysisService {
    /**
     * 资质解析列表及筛选
     * @param dicQuaAnalysis
     * @return
     */
    Map<String,Object> getQuaAnalysisList(DicQuaAnalysis dicQuaAnalysis);

    /**
     * 添加资质解析数据
     * @param param
     * @return
     */
    Map<String,Object> insertQuaAnalysis(Map<String,Object> param);

    /**
     * 删除资质解析数据
     * @param param
     * @return
     */
    Map<String,Object> delQuaAnalysis(Map<String,Object> param);
}
