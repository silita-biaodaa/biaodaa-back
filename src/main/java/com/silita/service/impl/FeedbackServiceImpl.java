package com.silita.service.impl;

import com.silita.dao.TbFeedbackMapper;
import com.silita.model.TbFeedback;
import com.silita.service.IFeedbackService;
import com.silita.service.abs.AbstractService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * tb_feedback ServiceImpl
 */
@Service
public class FeedbackServiceImpl extends AbstractService implements IFeedbackService {

    @Autowired
    TbFeedbackMapper tbFeedbackMapper;

    @Override
    public Map<String, Object> listFeedback(Map<String, Object> param) {
        TbFeedback feedback = new TbFeedback();
        feedback.setCurrentPage(MapUtils.getInteger(param, "currentPage"));
        feedback.setPageSize(MapUtils.getInteger(param, "pageSize"));
        int total = tbFeedbackMapper.queryFeedbackCount(feedback);
        if (total <= 0) {
            return new HashMap<>();
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("list", tbFeedbackMapper.queryFeedbackList(feedback));
        return super.handlePageCount(resultMap, feedback);
    }
}
