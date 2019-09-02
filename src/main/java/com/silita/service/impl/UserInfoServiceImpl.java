package com.silita.service.impl;

import com.silita.dao.SysLogsMapper;
import com.silita.dao.SysRoleInfoMapper;
import com.silita.dao.SysUserInfoMapper;
import com.silita.model.SysLogs;
import com.silita.model.SysUserInfo;
import com.silita.service.IUserInfoService;
import com.silita.service.abs.AbstractService;
import com.silita.service.mongodb.MongodbService;
import com.silita.utils.dateUtils.MyDateUtils;
import com.silita.utils.oldProjectUtils.CommonUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * sys_user_info ServiceImpl
 */
@Service
public class UserInfoServiceImpl extends AbstractService implements IUserInfoService {

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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String userType = MapUtils.getString(param, "userType");
        try {
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    String created = MyDateUtils.strToDates(MapUtils.getString(map, "created"), "yyyy-MM-dd");
                    map.put("created", created);
                    Integer integer = userTypeMap.get(MapUtils.getString(map, "pkid"));
                    if (integer != null && integer != 0) {
                        Date day = new Date();
                        String beginTime = MapUtils.getString(map, "expiredDate");
                        String endTime = format.format(day);
                        Date expiredDate = format.parse(beginTime);
                        Date current = format.parse(endTime);
                        int compareTo = expiredDate.compareTo(current);

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
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
/*
    public Map<String, Object> getActiveUserList(Map<String, Object> param) {
        SysUserInfo sysUserInfo = new SysUserInfo();
        String loginTime = MyDateUtils.getLoginTime();
        param.put("loginTime", loginTime);
        Map<String, Integer> userTypeMap = mongodbUtils.getUserType();
        List<Map<String, Object>> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String userType = MapUtils.getString(param, "userType");
        if (StringUtil.isNotEmpty(userType)) {
            list = sysUserInfoMapper.queryActiveUserList(param);
        } else {
            sysUserInfo.setCurrentPage(MapUtils.getInteger(param, "currentPage"));
            sysUserInfo.setPageSize(MapUtils.getInteger(param, "pageSize"));
            sysUserInfo.setLoginTime(loginTime);
            String phoneNo = MapUtils.getString(param, "phoneNo");
            if (StringUtil.isNotEmpty(phoneNo)) {
                sysUserInfo.setPhoneNo(phoneNo);
            }
            String startDate = MapUtils.getString(param, "startDate");
            String endDate = MapUtils.getString(param, "endDate");
            if (StringUtil.isNotEmpty(startDate)) {
                sysUserInfo.setStartDate(startDate);
            }
            if (StringUtil.isNotEmpty(endDate)) {
                sysUserInfo.setEndDate(endDate);
            }
            list = sysUserInfoMapper.queryActiveUserListAll(sysUserInfo);
        }
        try {
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    String created = MyDateUtils.strToDates(MapUtils.getString(map, "created"), "yyyy-MM-dd");
                    map.put("created", created);
                    Integer integer = userTypeMap.get(MapUtils.getString(map, "pkid"));
                    if (integer != null && integer != 0) {
                        Date day = new Date();
                        String beginTime = MapUtils.getString(map, "expiredDate");
                        String endTime = format.format(day);
                        Date expiredDate = format.parse(beginTime);
                        Date current = format.parse(endTime);
                        int compareTo = expiredDate.compareTo(current);

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
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> list1 = sysUserInfoMapper.queryActiveUserListAllCount(sysUserInfo);
        int count = list1.size();
        params.put("list", list);
        param.put("total",count);
        return super.handlePageCount(params, sysUserInfo);
    }
*/


    /**
     * 用户信息列表
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(Map<String, Object> param) {
        mongodbUtils.isNull(param);
        SysUserInfo sysUserInfo = new SysUserInfo();
        Map<String, Integer> userTypeMap = mongodbUtils.getUserType();
        List<Map<String, Object>> list = sysUserInfoMapper.queryUserInfoList(param);
        String userType = MapUtils.getString(param, "userType");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    String created = MyDateUtils.strToDates(MapUtils.getString(map, "created"), "yyyy-MM-dd");
                    map.put("created", created);
                    Integer integer = userTypeMap.get(MapUtils.getString(map, "pkid"));
                    if (integer != null && integer != 0) {
                        Date day = new Date();
                        String beginTime = MapUtils.getString(map, "expiredDate");
                        if (StringUtil.isNotEmpty(beginTime)) {
                            String endTime = format.format(day);
                            Date date1 = format.parse(beginTime);
                            Date date2 = format.parse(endTime);
                            int compareTo = date1.compareTo(date2);
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
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
/*
    @Override
    public Map<String, Object> getUserInfo(Map<String, Object> param) {
        SysUserInfo sysUserInfo = new SysUserInfo();
        Map<String, Integer> userTypeMap = mongodbUtils.getUserType();
        List<Map<String, Object>> list = new ArrayList<>();

        String userType = MapUtils.getString(param, "userType");
        if (StringUtil.isNotEmpty(userType)) {
            list = sysUserInfoMapper.queryUserInfoList(param);
        } else {
            sysUserInfo.setCurrentPage(MapUtils.getInteger(param, "currentPage"));
            sysUserInfo.setPageSize(MapUtils.getInteger(param, "pageSize"));
            String phoneNo = MapUtils.getString(param, "phoneNo");
            if (StringUtil.isNotEmpty(phoneNo)) {
                sysUserInfo.setPhoneNo(phoneNo);
            }
            String startDate = MapUtils.getString(param, "startDate");
            String endDate = MapUtils.getString(param, "endDate");
            if (StringUtil.isNotEmpty(startDate)) {
                sysUserInfo.setStartDate(startDate);
            }
            if (StringUtil.isNotEmpty(endDate)) {
                sysUserInfo.setEndDate(endDate);
            }
            String createStartDate = MapUtils.getString(param, "createStartDate");
            String createdEndData = MapUtils.getString(param, "createdEndData");
            if (StringUtil.isNotEmpty(createStartDate)) {
                sysUserInfo.setStartDate(createStartDate);
            }
            if (StringUtil.isNotEmpty(createdEndData)) {
                sysUserInfo.setEndDate(createdEndData);
            }
            list = sysUserInfoMapper.queryUserInfoListAll(sysUserInfo);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    String created = MyDateUtils.strToDates(MapUtils.getString(map, "created"), "yyyy-MM-dd");
                    map.put("created", created);
                    Integer integer = userTypeMap.get(MapUtils.getString(map, "pkid"));
                    if (integer != null && integer != 0) {
                        Date day = new Date();
                        String beginTime = MapUtils.getString(map, "expiredDate");
                        if (StringUtil.isNotEmpty(beginTime)) {
                            String endTime = format.format(day);
                            Date date1 = format.parse(beginTime);
                            Date date2 = format.parse(endTime);
                            int compareTo = date1.compareTo(date2);
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
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
        Map<String, Object> params = new HashMap<>();
        params.put("list", list);
        param.put("total", sysUserInfoMapper.queryUserInfoListAllCount(sysUserInfo));
        return super.handlePageCount(params, sysUserInfo);
    }
*/


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
                System.out.println(payCount);
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Map<String, Object> stringObjectMap : list) {
                Integer integer = pastDue.get(MapUtils.getString(stringObjectMap, "pkid"));
                if (null != integer && integer > 0) {
                    Date day = new Date();
                    String beginTime = MapUtils.getString(stringObjectMap, "expiredDate");
                    String endTime = sdf.format(day);
                    Date date1 = sdf.parse(beginTime);
                    Date date2 = sdf.parse(endTime);
                    int compareTo = date1.compareTo(date2);
                    if (compareTo < 0) {
                        pastCount++;
                    }
                }
            }
            map.put("pastUser", pastCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    /*
     */
/**
 * 获取用户统计
 *
 * @return
 *//*

    @Override
    public Map<String, Object> getUserCount() {
        Map<String, Object> param = new HashMap<>();
        String todays = MyDateUtils.getTodays();
        String yesterdays = MyDateUtils.getYesterdays();
        param.put("yesterday", yesterdays);
        param.put("today", todays);
        Map<String, Object> map = sysUserInfoMapper.queryUserInfoCount(param);
        try {
//            Map<String, Integer> userPayCount = mongodbUtils.getUserPayCount();
            Map<String, Integer> userPayCount = new HashMap<>();
            map.put("yesterdayPay", MapUtils.getInteger(userPayCount, "yesterdayPay"));
            map.put("todayPay", MapUtils.getInteger(userPayCount, "todayPay"));
            map.put("totalPayUser", MapUtils.getInteger(userPayCount, "totalPayUser"));
//            Map<String, Object> pastDue = mongodbUtils.getPastDue();
            Map<String, Object> pastDue;
            List<Map<String, Object>> list = sysUserInfoMapper.queryPast();
            int pastCount = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Map<String, Object> stringObjectMap : list) {
                String pkid = MapUtils.getString(pastDue, MapUtils.getString(stringObjectMap, "pkid"));
                String pkid = "";
                if (StringUtil.isNotEmpty(pkid)) {
                    Date day = new Date();
                    String beginTime = MapUtils.getString(stringObjectMap, "expiredDate");
                    String endTime = sdf.format(day);
                    Date date1 = sdf.parse(beginTime);
                    Date date2 = sdf.parse(endTime);
                    int compareTo = date1.compareTo(date2);
                    if (compareTo < 0) {
                        pastCount++;
                    }
                }
            }
            map.put("pastUser", pastCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
*/

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
        param.put("optType", "用户账号");
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    map.put("created", MapUtils.getString(map, "created"));
                    Integer integer = userTypeMap.get(MapUtils.getString(map, "pkid"));
                    if (integer != null && integer != 0) {
                        Date day = new Date();
                        String beginTime = MapUtils.getString(map, "expiredDate");
                        String endTime = format.format(day);
                        Date date1 = format.parse(beginTime);
                        Date date2 = format.parse(endTime);
                        int compareTo = date1.compareTo(date2);
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
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
}
