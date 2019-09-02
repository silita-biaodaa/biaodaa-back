package com.silita.service.impl;

import com.silita.dao.FeedbackMapper;
import com.silita.dao.SysLogsMapper;
import com.silita.dao.SysUserInfoMapper;
import com.silita.dao.TbFeedbackMapper;
import com.silita.model.TbFeedback;
import com.silita.service.IFeedbackService;
import com.silita.service.abs.AbstractService;
import com.silita.service.mongodb.MongodbService;
import com.silita.utils.dateUtils.MyDateUtils;
import com.silita.utils.oldProjectUtils.CommonUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tb_feedback ServiceImpl
 */
@Service
public class FeedbackServiceImpl extends AbstractService implements IFeedbackService {

    @Autowired
    TbFeedbackMapper tbFeedbackMapper;
    @Autowired
    FeedbackMapper feedbackMapper;
    @Autowired
    MongodbService mongodbService;
    @Autowired
    SysLogsMapper logsMapper;
    @Autowired
    SysUserInfoMapper sysUserInfoMapper;


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

    /**
     * 反馈列表
     *
     * @param param
     * @return
     */
    @Override
    public Map<String,Object> getlistFeedback(Map<String, Object> param) {
        Map<String, Integer> userTypeMap = mongodbService.getUserType();
        List<Map<String, Object>> list = feedbackMapper.queryFeedbackList(param);
        String userType = MapUtils.getString(param, "userType");
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                String starttime = MyDateUtils.strToDates(MapUtils.getString(map, "starttime"), "yyyy-MM-dd");
                map.put("starttime",starttime);
                Integer integer = userTypeMap.get(MapUtils.getString(map, "pkid"));
                judge(integer, map);
            }
        }
        if (StringUtil.isNotEmpty(userType)) {
            List<Map<String, Object>> listMap = list;
            List<Map<String, Object>> activeListMap = new ArrayList<>();
            for (Map<String, Object> map : listMap) {
                String userType1 = MapUtils.getString(map, "userType");
                if (userType.equals(userType1)) {
                    activeListMap.add(map);
                }
            }
            Integer currentPage = MapUtils.getInteger(param, "currentPage");
            Integer pageSize = MapUtils.getInteger(param, "pageSize");
            return super.getPagingResultMap(activeListMap, currentPage, pageSize);
        }
        Integer currentPage = MapUtils.getInteger(param, "currentPage");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        return super.getPagingResultMap(list, currentPage, pageSize);
    }

    public void judge(Integer integer, Map<String, Object> map) {
        if (integer != null && integer != 0) {
            String beginTime = MapUtils.getString(map, "expiredDate");
            if (StringUtil.isNotEmpty(beginTime)) {
                Integer compareTo = MyDateUtils.getCompareTo(beginTime);
                if (compareTo < 0) {
                    map.put("userType", "过期");
                } else {
                    if (integer > 1) {
                        map.put("userType", "续费");
                    } else {
                        map.put("userType", "付费");
                    }
                }
            } else {
                map.put("userType", "注册");
            }
        } else {
            map.put("userType", "注册");
        }
    }

    /**
     * 反馈统计
     *
     * @return
     */
    @Override
    public Map<String, Object> getFeedbackCount() {
        Map<String, Object> param = new HashMap<>();
        param.put("yesterday", MyDateUtils.getTodays());
        return feedbackMapper.queryFeedbackCount(param);
    }

    /**
     * 修改备注
     * @param param
     */
    @Override
    public void updateRemark(Map<String, Object> param) {
        param.put("pid", CommonUtil.getUUID());
        param.put("optType", "意见反馈");
        param.put("optDesc", "添加反馈备注");
        param.put("pkid",feedbackMapper.queryPid(param));
        String phone = sysUserInfoMapper.queryPhoneSingle(param);
        param.put("operand", phone);
        logsMapper.insertLogs(param);//添加操作日志
        feedbackMapper.updateRemark(param);
    }

    /**
     * 修改反馈状态
     * @param param
     */
    @Override
    public void updateState(Map<String, Object> param) {
        param.put("pid", CommonUtil.getUUID());
        param.put("optType", "意见反馈");
        param.put("optDesc", "编辑反馈状态");
        param.put("pkid",feedbackMapper.queryPid(param));
        String phone = sysUserInfoMapper.queryPhoneSingle(param);
        param.put("operand", phone);
        logsMapper.insertLogs(param);//添加操作日志
        feedbackMapper.updateState(param);
    }


}
