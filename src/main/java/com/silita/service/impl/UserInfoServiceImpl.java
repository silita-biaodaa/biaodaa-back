package com.silita.service.impl;

import com.silita.dao.SysUserInfoMapper;
import com.silita.model.SysUserInfo;
import com.silita.service.IUserInfoService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.dateUtils.MyDateUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * sys_user_info ServiceImpl
 */
@Service
public class UserInfoServiceImpl extends AbstractService implements IUserInfoService {

    @Autowired
    SysUserInfoMapper sysUserInfoMapper;

    @Override
    public Map<String, Object> userCount(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        param.put("date", MyDateUtils.getTime("yyyy-MM-dd"));
        //今天个数
        resultMap.put("todayCount", sysUserInfoMapper.queryUserCount(param));
        //昨天个数
        param.put("date", MyDateUtils.getYestdayBaseToday());
        resultMap.put("yesterdayCount", sysUserInfoMapper.queryUserCount(param));
        //总数
        SysUserInfo user = new SysUserInfo();
        user.setType("count");
        if (null != param.get("provCode")) {
            user.setProvCode(MapUtils.getString(param, "provCode"));
        }
        resultMap.put("total", sysUserInfoMapper.queryUserTotal(user));
        return resultMap;
    }

    @Override
    public void userLock(Map<String, Object> param) {
        sysUserInfoMapper.lockUser(param);
    }

    @Override
    public Map<String, Object> listUserInfo(SysUserInfo userInfo) {
        Integer total = sysUserInfoMapper.queryUserTotal(userInfo);
        if (total <= 0) {
            return new HashMap<>();
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("list", sysUserInfoMapper.queryUserList(userInfo));
        return super.handlePageCount(resultMap, userInfo);
    }
}
