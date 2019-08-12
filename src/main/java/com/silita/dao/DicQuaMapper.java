package com.silita.dao;

import com.silita.model.DicQua;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface DicQuaMapper extends MyMapper<DicQua> {

    /**
     * 添加资质
     * @param qua
     * @return
     */
    int insertDicQual(DicQua qua);

    /**
     * 修改资质
     * @param qua
     * @return
     */
    int updateDicQual(DicQua qua);

    /**
     * 修改资质
     * @param qua
     * @return
     */
    int updateQual(DicQua qua);

    /**
     * 删除资质
     * @param id
     * @return
     */
    int delDicQual(String id);

    /**
     * 查询资质
     * @param param
     * @return
     */
    List<DicQua> queryDicQuaList(Map<String,Object> param);

    /**
     * 查询资质类别
     * @return
     */
    List<Map<String,Object>> queryQualCateList();

    /**
     * 查询资质count
     * @param param
     * @return
     */
    Integer queryQualCountByName(Map<String,Object> param);

    /**
     * 根据资质code查询详情
     * @param quaCode
     * @return
     */
    DicQua queryQualDetail(String quaCode);

    /**
     * 根据主键查询详情
     * @param pkid
     * @return
     */
    DicQua queryQualDetailById(String pkid);

    /**
     * 根据资质code查询详情
     * @param quaName
     * @return
     */
    DicQua queryQualDetailName(DicQua quaName);

    /**
     * 根据资质code查询详情
     * @param quaName
     * @return
     */
    DicQua queryQualDetailParentName(String quaName);

    /**
     * 根据标准名称查询资质code
     * @param quaName
     * @return
     */
    String queryQualCodeByBenchName(String quaName);

    /**
     * 查询资质标准名
     * @return
     */
    List<Map<String,Object>> queryBenchName();




    /**
     * 获取资质大类
     */

    List<Map<String,Object>> queryQuaOne(Map<String,Object> param);

    /**
     * 获取资质最后一级标准名称-
     */
    List<Map<String,Object>> queryQuaTwo(Map<String,Object> param);

    List<Map<String,Object>> queryQuaparentId(Map<String,Object> param);

    Integer queryQuaparentIdIsNull(Map<String,Object> param);

    List<Map<String,Object>> queryDicQuaListMap(Map<String,Object> param);

    List<Map<String,Object>> queryDicQuaBenchNameListMap(Map<String,Object> param);
}