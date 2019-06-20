package com.silita.dao;

import com.silita.model.TbCompanyInfoHm;
import com.silita.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TbCompanyInfoHmMapper extends MyMapper<TbCompanyInfoHm> {

    /**
     * 查询人工添加企业列表
     * @param companyInfoHm
     * @return
     */
    List<TbCompanyInfoHm> queryCompanyInfoHmList(TbCompanyInfoHm companyInfoHm);

    /**
     * 查询人工添加企业列表数
     * @param companyInfoHm
     * @return
     */
    Integer queryCompanyCount(TbCompanyInfoHm companyInfoHm);

    /**
     * 添加企业
     * @param companyInfoHm
     * @return
     */
    Integer insertCompanyInfo(TbCompanyInfoHm companyInfoHm);

    /**
     * 修改企业
     * @param companyInfoHm
     * @return
     */
    Integer updateCompanyInfo(TbCompanyInfoHm companyInfoHm);

    /**
     * 删除企业
     * @param pkid
     * @return
     */
    Integer deleteCompanyInfo(String pkid);

    /**
     * 查询企业个数根据comid
     * @param param
     * @return
     */
    Integer queryCompanyCountByComId(Map<String,Object> param);

    /**
     * 修改企业信息根据comid
     * @param companyInfoHm
     * @return
     */
    int updateCompanyByComId(TbCompanyInfoHm companyInfoHm);

    /**
     * 删除统一信用代码(逻辑删除)
     * @param comId
     * @return
     */
    int delCompanyCreditCode(String comId);

    /**
     * 查询统一信用代码
     * @param companyInfoHm
     * @return
     */
    TbCompanyInfoHm queryCompanyCreditCode(TbCompanyInfoHm companyInfoHm);

    /**
     * 查询变更企业名称
     * @param param
     * @return
     */
    List<TbCompanyInfoHm> queryCompanyDetailHm(Map<String,Object> param);

    /**
     * 查询人工编辑企业名称数
     * @param companyInfoHm
     * @return
     */
    Integer queryComNameCount(TbCompanyInfoHm companyInfoHm);

    /**
     * 根据企业名称或拼音获取信息
     * @param param
     * @return
     */
    List<Map<String, Object>> listComNameCountByNameOrPinYin(Map<String,Object> param);
}