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
}