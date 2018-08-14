package com.silita.service;

import com.silita.model.DicAlias;
import com.silita.model.DicCommon;

import java.util.List;
import java.util.Map;

/**
 * 等级 service
 */
public interface IGradeService {

    /**
     * 等级
     * @param param
     * @return
     */
    List<Map<String, Object>> getGradeList(Map<String, Object> param);

    /**
     * 保存
     * @param dicCommon
     * @param username
     */
    void saveGrade(DicCommon dicCommon,String username);

    /**
     * 删除等级
     * @param param
     */
    void delGrade(Map<String,Object> param);

    /**
     * 添加等级别名
     * @param alias
     */
    void addGradeAlias(DicAlias alias);
}
