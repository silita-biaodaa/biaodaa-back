package com.silita.service;

import com.silita.model.DicAlias;
import com.silita.model.DicQua;

import java.util.List;
import java.util.Map;

/**
 * 资质接口
 */
public interface IQualService {


    /**
     * 添加资质
     * @param param
     * @return
     */
    Map<String,Object> addQual(Map<String,Object> param);

    /**
     * 删除资质
     *
     * @param param
     */
    void delQual(Map<String,Object> param);



    Map<String, Object> updQuals(Map<String, Object> param);

    /**
     * 获取资质类别
     *
     * @return
     */
    List<Map<String, Object>> getQualCateList();

    /**
     * 获取资质列表
     *
     * @param param
     * @return
     */
    Map<String,Object> getDicQuaListMaps(Map<String,Object> param);




    /**
     * 添加资质别名
     *
     * @param alias
     */
    Map<String, Object> aliasAdd(DicAlias alias);

    /**
     * 添加资质别名,去重
     * @param dicAliasList
     */
    void addQuaAlias(List<DicAlias> dicAliasList);

    /**
     * 修改资质别名
     * @param alias
     * @return
     */
    Map<String,Object> updateQuaAlias(DicAlias alias);

    /**
     * 获取全部资质等级
     * @return
     */
    List<Map<String,Object>> qualGradeList();

    List<Map<String,Object>> listQual();

    /**
     * 获取资质
     *
     */
    List<Map<String, Object>> getQua();

    /**
     * 获取资质属性
     * @param param
     * @return
     */
    Map<String,Object> getBizType(Map<String,Object> param);

    /**
     * 修改资质属性
     * @param param
     */
    void updateBizType(Map<String,Object> param);

    /**
     * 资质解析列表筛选
     * @param dicQua
     * @return
     */
    Map<String,Object> getQualAnalysis(DicQua dicQua);

    /**
     * 添加资质解析词典
     * @param param
     * @return
     */
    Map<String,Object> getaddAilas(Map<String,Object> param);
}
