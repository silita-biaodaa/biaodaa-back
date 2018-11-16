package com.silita.dao;

import com.silita.model.TbCompanyHighwayGrade;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface TbCompanyHighwayGradeMapper extends MyMapper<TbCompanyHighwayGrade> {

    /**
     * 查询信用等级
     * @param tbCompanyHighwayGrade
     * @return
     */
    List<Map<String,Object>> queryCompanyHigway(TbCompanyHighwayGrade tbCompanyHighwayGrade);

    /**
     * 添加
     * @param tbCompanyHighwayGrade
     * @return
     */
    Integer insertCompanyHigway(TbCompanyHighwayGrade tbCompanyHighwayGrade);

    /**
     * 修改
     * @param tbCompanyHighwayGrade
     * @return
     */
    Integer updateCompanyHigway(TbCompanyHighwayGrade tbCompanyHighwayGrade);

    /**
     * 删除
     * @param pkid
     * @return
     */
    Integer deleteCompanyHigway(String pkid);

    /**
     * 根据条件删除
     * @param list
     * @return
     */
    Integer deleteCompanyHigwayForParam(List<Map<String,Object>> list);

    /**
     * 查询企业的信用等级评定地区
     * @param comId
     * @return
     */
    List<Map<String,Object>> queryAssessPro(String comId);

    /**
     * 查询企业信用等级详情
     * @param grade
     * @return
     */
    TbCompanyHighwayGrade queryCompanyHighwanGradeDetail(TbCompanyHighwayGrade grade);

    /**
     *  企业公路信用等级列表
     * @return
     */
    List<Map<String,Object>> queryCompanyHigForCompanyList(TbCompanyHighwayGrade grade);

    /**
     *  企业公路信用等级列表
     * @return
     */
    Integer queryCompanyHigForCompanyCount(TbCompanyHighwayGrade grade);

    /**
     * 批量添加
     * @param list
     * @return
     */
    int batchInsertCompanyHig(List<TbCompanyHighwayGrade> list);

    /**
     * 查询个数
     * @param param
     * @return
     */
    Integer queryCount(Map<String,Object> param);
}