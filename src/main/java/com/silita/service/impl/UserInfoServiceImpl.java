package com.silita.service.impl;

import com.silita.common.UserTypeCommon;
import com.silita.dao.SysLogsMapper;
import com.silita.dao.SysRoleInfoMapper;
import com.silita.dao.SysUserInfoMapper;
import com.silita.model.SysUserInfo;
import com.silita.service.IUserInfoService;
import com.silita.service.abs.AbstractService;
import com.silita.service.mongodb.MongodbService;
import com.silita.utils.dateUtils.MyDateUtils;
import com.silita.utils.oldProjectUtils.CommonUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * sys_user_info ServiceImpl
 */
@Service
public class UserInfoServiceImpl extends AbstractService implements IUserInfoService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    @Autowired
    SysUserInfoMapper sysUserInfoMapper;
    @Autowired
    SysRoleInfoMapper sysRoleInfoMapper;
    @Autowired
    MongodbService mongodbUtils;
    @Autowired
    SysLogsMapper logsMapper;

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

    /**
     * 锁定用户
     *
     * @param param
     */
    @Override
    public void userLock(Map<String, Object> param) {
        param.put("pid", CommonUtil.getUUID());
        param.put("optType", "用户账号");
        if (MapUtils.getInteger(param, "lock") == 1) {
            param.put("optDesc", "解锁账户");
        } else {
            param.put("optDesc", "锁定账户");
        }
        String phone = sysUserInfoMapper.queryPhoneSingle(param);
        param.put("operand", phone);
        logsMapper.insertLogs(param);//添加操作日志
        sysUserInfoMapper.lockUser(param);
    }

    /**
     * 查询列表
     *
     * @param userInfo
     * @return
     */
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

    /**
     * 统计活跃用户
     *
     * @return
     */
    @Override
    public Map<String, Object> getActiveUserCount() {
        Map<String, Object> param = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar yester = Calendar.getInstance();
        yester.add(Calendar.DATE, -1);
        Calendar to = Calendar.getInstance();
        to.add(Calendar.DATE, 0);//-1.昨天时间 0.当前时间 1.明天时间 *以此类推
        String yesterday = sdf.format(yester.getTime());
        String today = sdf.format(to.getTime());
        param.put("yesterday", yesterday);
        param.put("today", today);
        return sysUserInfoMapper.queryActiveUserCount(param);
    }


    /**
     * 获取活跃用户列表
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getActiveUserList(Map<String, Object> param) {
        mongodbUtils.isNull(param);
        Map<String, Integer> userTypeMap = mongodbUtils.getUserType();
        List<Map<String, Object>> list = sysUserInfoMapper.queryActiveUserList(param);
        String userType = MapUtils.getString(param, "userType");
        try {
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    String created = MyDateUtils.strToDates(MapUtils.getString(map, "created"), "yyyy-MM-dd");
                    map.put("created", created);
                    Integer integer = userTypeMap.get(MapUtils.getString(map, "pkid"));
                    UserTypeCommon.judge(integer, map);
                }
            }
        } catch (Exception e) {
            logger.error("获取活跃用户列表", e);
        }
        if (StringUtil.isNotEmpty(userType)) {
            return isNullUserType(param, list, userType);
        }
        Integer currentPage = MapUtils.getInteger(param, "currentPage");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        return super.getPagingResultMap(list, currentPage, pageSize);
    }


    /**
     * 用户信息列表
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(Map<String, Object> param) {
        mongodbUtils.isNull(param);
        Map<String, Integer> userTypeMap = mongodbUtils.getUserType();
        List<Map<String, Object>> list = sysUserInfoMapper.queryUserInfoList(param);
        String userType = MapUtils.getString(param, "userType");
        try {
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    String created = MyDateUtils.strToDates(MapUtils.getString(map, "created"), "yyyy-MM-dd");
                    Integer integer = userTypeMap.get(MapUtils.getString(map, "pkid"));
                    map.put("created", created);
                    UserTypeCommon.judge(integer, map);
                }
            }
        } catch (Exception e) {
            logger.error("用户信息列表", e);
        }
        if (StringUtil.isNotEmpty(userType)) {
            return isNullUserType(param, list, userType);
        }
        Integer currentPage = MapUtils.getInteger(param, "currentPage");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        return super.getPagingResultMap(list, currentPage, pageSize);
    }

    /**
     * 获取用户统计
     *
     * @return
     */
    @Override
    public Map<String, Object> getUserCount() {
        Map<String, Object> param = new HashMap<>();
        String todays = MyDateUtils.getTodays();
        String yesterdays = MyDateUtils.getYesterdays();
        param.put("yesterday", yesterdays);
        param.put("today", todays);
        Map<String, Object> map = sysUserInfoMapper.queryUserInfoCount(param);
        try {
            Map<String, Integer> userPayCount = mongodbUtils.getUserPayCount();
            List<Map<String, Object>> userPayCounts = mongodbUtils.getUserPayCounts();
            param.put("listUserId", userPayCounts);
            Map<String, Object> maps = new HashMap<>();
            List<Map<String, Object>> lists = sysUserInfoMapper.queryPhones(param);
            for (Map<String, Object> userId : lists) {
                maps.put(MapUtils.getString(userId, "pkid"), MapUtils.getString(userId, "phoneNo"));
            }
            int count = 0;
            for (Map<String, Object> payCount : userPayCounts) {
                String phone = MapUtils.getString(maps, MapUtils.getString(payCount, "userId"));
                if (StringUtil.isNotEmpty(phone)) {
                    count++;
                }
            }
            map.put("yesterdayPay", MapUtils.getInteger(userPayCount, "yesterdayPay"));
            map.put("todayPay", MapUtils.getInteger(userPayCount, "todayPay"));
            map.put("totalPayUser", count);
            Map<String, Integer> pastDue = mongodbUtils.getUserType();
            List<Map<String, Object>> list = sysUserInfoMapper.queryPast();
            int pastCount = 0;
            for (Map<String, Object> stringObjectMap : list) {
                Integer integer = pastDue.get(MapUtils.getString(stringObjectMap, "pkid"));
                if (null != integer && integer > 0) {
                    String beginTime = MapUtils.getString(stringObjectMap, "expiredDate");
                    Integer compareTo = MyDateUtils.getCompareTo(beginTime);
                    if(null != compareTo && null != beginTime){
                        if (compareTo < 0) {
                            pastCount++;
                        }
                    }
                }
            }
            map.put("pastUser", pastCount);
        } catch (Exception e) {
            logger.error("获取用户统计", e);
        }
        return map;
    }


    /**
     * 个人信息
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getSingleUserInfo(Map<String, Object> param) {
        Map<String, Object> map = sysUserInfoMapper.querySingleUserInfo(param);
        String inviterCode = MapUtils.getString(map, "inviterCode");
        param.put("inviterCode", inviterCode);
        String phone = sysUserInfoMapper.queryInviterPhone(param);
        if (StringUtil.isNotEmpty(phone)) {
            map.put("inviterPhone", phone);
        }
        return sysUserInfoMapper.querySingleUserInfo(param);
    }

    /**
     * 编辑备注
     *
     * @param param
     */
    @Override
    public void updateRemark(Map<String, Object> param) {
        param.put("pid", CommonUtil.getUUID());
        param.put("optType", "用户信息");
        param.put("optDesc", "添加用户备注");
        String phone = sysUserInfoMapper.queryPhoneSingle(param);
        param.put("operand", phone);
        logsMapper.insertLogs(param);//添加操作日志
        sysUserInfoMapper.updateRemark(param);
    }

    /**
     * 邀请人信息
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getInviterInfo(Map<String, Object> param) {
        Map<String, Integer> userTypeMap = mongodbUtils.getUserType();
        List<Map<String, Object>> list = sysUserInfoMapper.queryInviterInfo(param);
        try {
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    map.put("created", MapUtils.getString(map, "created"));
                    Integer integer = userTypeMap.get(MapUtils.getString(map, "pkid"));
                    UserTypeCommon.judge(integer, map);
                }
            }
        } catch (Exception e) {
            logger.error("邀请人信息", e);
        }
        Map<String, Object> resultMap = new HashMap<>();
        int count = 0;
        if (list.size() > 0) {
            count = list.size();
        }
        resultMap.put("list", list);
        resultMap.put("total", count);
        return resultMap;
    }

    /**
     * 订单统计
     *
     * @return
     */
    @Override
    public Map<String, Object> getOrderCount() {
        return mongodbUtils.getOrderCount();
    }


    public Map<String, Object> isNullUserType(Map<String, Object> param, List<Map<String, Object>> list, String userType) {
        List<Map<String, Object>> activeListMap = new ArrayList<>();
        List<Map<String, Object>> listMap = list;
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
}
