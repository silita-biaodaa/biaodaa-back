package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.DicAliasMapper;
import com.silita.dao.DicCommonMapper;
import com.silita.dao.DicQuaAnalysisMapper;
import com.silita.dao.RelQuaGradeMapper;
import com.silita.model.DicQuaAnalysis;
import com.silita.service.IDicQuaAnalysisService;
import com.silita.service.abs.AbstractService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DicQuaAnalysisServiceImpl extends AbstractService implements IDicQuaAnalysisService {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(DicQuaAnalysisServiceImpl.class);
    @Autowired
    private DicQuaAnalysisMapper dicQuaAnalysisMapper;
    @Autowired
    private RelQuaGradeMapper relQuaGradeMapper;
    @Autowired
    private DicAliasMapper dicAliasMapper;
    @Autowired
    private DicCommonMapper dicCommonMapper;

    /**
     * 资质解析列表及筛选
     *
     * @param dicQuaAnalysis
     * @return
     */
    @Override
    public Map<String, Object> getQuaAnalysisList(DicQuaAnalysis dicQuaAnalysis) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("list", dicQuaAnalysisMapper.queryQuaAnalysisList(dicQuaAnalysis));
            resultMap.put("total", dicQuaAnalysisMapper.queryQuaAnalysisListCount(dicQuaAnalysis));
        } catch (Exception e) {
            logger.error("资质解析列表及筛选", e);
        }
        return super.handlePageCount(resultMap, dicQuaAnalysis);
    }
    /**
     * 添加资质解析数据
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> insertQuaAnalysis(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Integer integer = dicQuaAnalysisMapper.queryJointAilas(param);//判断组合别名是否存在
            if(null != integer && integer > 0){
                resultMap.put("code", "0");
                resultMap.put("msg", "别名已存在");
                return resultMap;
            }
            dicQuaAnalysisMapper.insertAanlysisOne(param);//添加资质解析组合数据
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
        } catch (Exception e) {
            logger.error("添加资质解析词典", e);
        }
        return resultMap;
    }

    /**
     * 删除资质解析数据
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> delQuaAnalysis(Map<String, Object> param) {
        Map<String,Object> resultMap = new HashMap<>();
        dicQuaAnalysisMapper.deleteAanlysis(param);//删除资质解析组合数据
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }
}
