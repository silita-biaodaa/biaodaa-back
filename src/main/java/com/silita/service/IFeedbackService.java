package com.silita.service;

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
}
