package com.silita.service;

import java.util.List;
import java.util.Map;

/**
 * tb_feedback Service
 */
public interface IFeedbackService {

    /**
     * 列表展示
     * @param param
     * @return
     */
    Map<String,Object> listFeedback(Map<String,Object> param);

    /**
     * 反馈列表
     * @param param
     * @return
     */
    Map<String,Object> getlistFeedback(Map<String,Object> param);

    /**
     * 反馈统计
     * @return
     */
    Map<String,Object> getFeedbackCount();

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
}
