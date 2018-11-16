package com.silita.dao;

import com.silita.model.TbFeedback;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * tb_feecback Mapper
 */
public interface TbFeedbackMapper extends MyMapper<TbFeedback> {

    /**
     * 查询列表
     * @param tbFeedback
     * @return
     */
    List<Map<String,Object>> queryFeedbackList(TbFeedback tbFeedback);

    /**
     * 查询个数
     * @param tbFeedback
     * @return
     */
    int queryFeedbackCount(TbFeedback tbFeedback);
}