package com.silita.dao;

import com.silita.model.Feedback;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * 反馈
 */
public interface FeedbackMapper extends MyMapper<Feedback> {
    /**
     * 反馈列表
     * @param param
     * @return
     */
    List<Map<String,Object>> queryFeedbackList(Map<String,Object> param);

    /**
     * 反馈统计 今日、昨日
     * @param param
     * @return
     */
    Map<String,Object> queryFeedbackCount(Map<String,Object> param);

    /**
     * 修改备注
     * @param param
     */
    void updateRemark(Map<String,Object> param);

    /**
     * 修改反馈状态
     * @param param
     */
    void updateState(Map<String,Object> param);

    /**
     * 获取用户id
     * @param param
     * @return
     */
    String queryPid(Map<String,Object> param);

}