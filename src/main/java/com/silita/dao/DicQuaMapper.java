package com.silita.dao;

import com.silita.model.DicQua;
import com.silita.utils.MyMapper;
import com.sun.corba.se.spi.ior.ObjectKey;

import java.util.List;
import java.util.Map;

public interface DicQuaMapper extends MyMapper<DicQua> {

    /**
     * 添加资质
     *
     * @param param
     * @return
     */
    int insertDicQual(Map<String,Object> param);

    /**
     * 修改资质
     *
     * @param param
     * @return
     */
    void updateDicQual(Map<String,Object> param);

    /**
     * 修改资质
     *
     * @param qua
     * @return
     */
    int updateQual(DicQua qua);

    /**
     * 删除资质
     *
     * @param param
     * @return
     */
    int delDicQual(Map<String,Object> param);

    /**
     * 获取标准名称是否存在
     * @param param
     * @return
     */
    Integer querySingleBenchName(Map<String,Object> param);
    /**
     * 获取标准名称是否存在
     * @param param
     * @return
     */
    String querySingleBenchNames(Map<String,Object> param);
    /**
     * 获取资质名称是否存在
     * @param param
     * @return
     */
    Integer querySingleQuaName(Map<String,Object> param);

    /**
     * 获取标准名称是否存在
     * @param param
     * @return
     */
    String querySingleQuaNames(Map<String,Object> param);

    /**
     * 获取等级是几级
     * @param param
     * @return
     */
    Integer queryLevel(Map<String,Object> param);


    /**
     * 获取资质code
     * @param param
     * @return
     */
    String queryCode(Map<String,Object> param);

    /**
     * 查询资质
     *
     * @param param
     * @return
     */
    List<DicQua> queryDicQuaList(Map<String, Object> param);

    /**
     * 查询资质类别
     *
     * @return
     */
    List<Map<String, Object>> queryQualCateList();

    /**
     * 查询资质count
     *
     * @param param
     * @return
     */
    Integer queryQualCountByName(Map<String, Object> param);

    /**
     * 根据资质code查询详情
     *
     * @param quaCode
     * @return
     */
    DicQua queryQualDetail(String quaCode);

    /**
     * 根据主键查询详情
     *
     * @param pkid
     * @return
     */
    DicQua queryQualDetailById(String pkid);

    /**
     * 根据资质code查询详情
     *
     * @param quaName
     * @return
     */
    DicQua queryQualDetailName(DicQua quaName);

    /**
     * 根据资质code查询详情
     *
     * @param quaName
     * @return
     */
    DicQua queryQualDetailParentName(String quaName);

    /**
     * 根据标准名称查询资质code
     *
     * @param quaName
     * @return
     */
    String queryQualCodeByBenchName(String quaName);

    /**
     * 查询资质标准名
     *
     * @return
     */
    List<Map<String, Object>> queryBenchName();


    List<Map<String, Object>> queryQuaparentId(Map<String, Object> param);

    Integer queryQuaparentIdIsNull(Map<String, Object> param);

    /**
     * 获取资质标准名称
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> queryDicQuaListMap(Map<String, Object> param);

    List<Map<String, Object>> queryDicQuaListMaps(Map<String, Object> param);

    /**
     * 获取所有资质
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> queryDicQuaBenchNameListMap(Map<String, Object> param);

    /**
     * 获取一级资质
     */
    List<Map<String, Object>> queryQuaOne(Map<String, Object> param);

    /**
     * 获取二级资质
     */
    List<Map<String, Object>> queryQuaTwo(Map<String, Object> param);

    /**
     * 获取三级资质
     * @param param
     * @return
     */
    List<Map<String, Object>> queryQuaThree(Map<String, Object> param);

    /**
     * 获取四级资质
     * @param param
     * @return
     */
    List<Map<String, Object>> queryQuaFout(Map<String, Object> param);

    /**
     * 获取标准名称
     * @param param
     * @return
     */
    List<String> queryBenchNames(Map<String,Object> param);


    /**
     * 获取一级资质
     */

    List<Map<String, Object>> queryQuaOnes(Map<String, Object> param);

    /**
     * 获取资质级别
     */
    List<Map<String, Object>> queryQuaTwos(Map<String, Object> param);

    /**
     * 获取资质属性
     * @param param
     * @return
     */
    Map<String,Object> queryBizType(Map<String,Object> param);

    /**
     * 修改资质属性
     */
    void updateBizType(Map<String,Object> param);

    /**
     * 获取资质是否存在  并且获取资质等级
     * @return
     */
    Map<String,Object> queryQuaLevel(Map<String,Object> param);

    /**
     * 资质解析维护列表
     * @param dicQua
     * @return
     */
    List<Map<String,Object>> queryQualAnalysis(DicQua dicQua);

    /**
     * 统计资质解析维护列表统计
     * @param dicQua
     * @return
     */
    Integer queryQualAnalysisCount(DicQua dicQua);

    /**
     * 资质解析维护列表
     * @param dicQua
     * @return
     */
    List<Map<String,Object>> queryQualAnalysisList(DicQua dicQua);

    /**
     * 获取资质解析维护需要添加的数据
     * @param param
     * @return
     */
    List<Map<String,Object>> queryQualAnalysisLists(Map<String,Object> param);
    /**
     * 获取资质解析维护需要添加的数据
     * @param param
     * @return
     */
    List<Map<String,Object>> queryQualAnalysisOne(Map<String,Object> param);
    /**
     * 获取资质解析维护需要添加的数据
     * @param param
     * @return
     */
    List<Map<String,Object>> queryQualAnalysisTow(Map<String,Object> param);



    /**
     * 统计资质解析维护列表统计
     * @param dicQua
     * @return
     */
    Integer queryQualAnalysisListCount(DicQua dicQua);


    /**
     * 根据标准名称查询是否存在该资质
     * @return
     */
    String queryQuaCodeBenchName(Map<String,Object> param);

    /**
     * 根据quaCode获取标准名称
     * @param param
     * @return
     */
    String queryBenchNameQuaCode(Map<String,Object> param);

    /**
     * 获取一级资质 用于redis缓存
     */

    List<Map<String, Object>> queryQuaOneRedis(Map<String, Object> param);

    /**
     * 获取资质级别 用于redis缓存
     */
    List<Map<String, Object>> queryQuaTwoRedis(Map<String, Object> param);

}