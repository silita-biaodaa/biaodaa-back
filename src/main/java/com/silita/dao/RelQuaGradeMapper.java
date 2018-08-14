package com.silita.dao;

import com.silita.model.RelQuaGrade;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * 资质等级 Mapper
 */
public interface RelQuaGradeMapper extends MyMapper<RelQuaGrade> {

    /**
     * 获取资质下的等级
     * @param param
     * @return
     */
    List<RelQuaGrade> queryQuaGrade(Map<String,Object> param);
}