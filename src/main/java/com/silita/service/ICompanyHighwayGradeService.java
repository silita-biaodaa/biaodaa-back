package com.silita.service;

import com.silita.model.TbCompanyHighwayGrade;

import java.util.List;
import java.util.Map;

/**
 * tb_company_highway_grade service
 */
public interface ICompanyHighwayGradeService {

    /**
     * 获取信用等级
     * @param grade
     * @return
     */
    List<Map<String,Object>> getCompanyHighwayGradeList(TbCompanyHighwayGrade grade);

    /**
     * 获取信用等级地区
     * @param param
     * @return
     */
    List<Map<String,Object>> getHighwayProv(Map<String,Object> param);

    /**
     * 添加信用等级
     * @param grade
     * @param username
     * @return
     */
    Map<String,Object> addHighway(TbCompanyHighwayGrade grade,String username);
}
