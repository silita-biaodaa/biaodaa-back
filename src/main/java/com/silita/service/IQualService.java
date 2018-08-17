package com.silita.service;

import com.silita.model.DicAlias;
import com.silita.model.DicQua;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 资质接口
 */
public interface IQualService {

    /**
     * 添加资质
     *
     * @param qua
     * @param username
     */
    Map<String, Object> addQual(DicQua qua, String username);

    /**
     * 删除资质
     *
     * @param id
     */
    void delQual(String id);

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
    List<DicQua> getDicQuaList(Map<String, Object> param);

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
}
