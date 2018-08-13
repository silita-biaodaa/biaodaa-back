package com.silita.service;

import com.silita.model.DicQua;

import java.util.List;
import java.util.Map;

/**
 * 资质接口
 */
public interface IQualService {

    /**
     * 添加资质
     * @param qua
     */
    void addQual(DicQua qua);

    /**
     * 删除资质
     * @param id
     */
    void delQual(String id);

    /**
     * 获取资质类别
     * @return
     */
    List<Map<String,Object>> getQualCateList();

    /**
     * 获取资质列表
     * @param param
     * @return
     */
    List<DicQua> getDicQuaList(Map<String,Object> param);
}
