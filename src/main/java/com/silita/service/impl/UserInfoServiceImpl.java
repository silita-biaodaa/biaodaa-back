package com.silita.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.silita.common.MongodbCommon;
import com.silita.dao.SysRoleInfoMapper;
import com.silita.dao.SysUserInfoMapper;
import com.silita.model.SysUserInfo;
import com.silita.service.IUserInfoService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.PropertiesUtils;
import com.silita.utils.dateUtils.MyDateUtils;
import com.silita.utils.mongdbUtlis.MongoUtils;
import com.silita.utils.stringUtils.StringUtils;
import org.apache.commons.collections.MapUtils;
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

        String loginTime = getLoginTime();
        param.put("loginTime", loginTime);

        Map<String, Integer> userTypeMap = MongodbCommon.getUserType();

        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> list = sysUserInfoMapper.queryActiveUserList(param);
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
                        Date expiredDate = format.parse(beginTime);
                        Date current = format.parse(endTime);
                        int compareTo = expiredDate.compareTo(current);
                        if (compareTo < 0) {
                            map.put("userType", "过期");
                        } else {
                            map.put("userType", "付费");
                        }
                    } else {
                        map.put("userType", "注册");
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String userType = MapUtils.getString(param, "userType");
        if (StringUtil.isNotEmpty(userType)) {
            List<Map<String, Object>> listMap = list;
            List<Map<String, Object>> activeListMap = new ArrayList<>();
            for (Map<String, Object> map : listMap) {
                String userType1 = MapUtils.getString(map, "userType");
                Map<String, Object> maps = new HashMap<>();
                if (userType.equals(userType1)) {
                    maps.put("pkid", MapUtils.getString(map, "pkid"));
                    maps.put("phoneNo", MapUtils.getString(map, "phoneNo"));
                    maps.put("loginCount", MapUtils.getString(map, "loginCount"));
                    maps.put("loginName", MapUtils.getString(map, "loginName"));
                    maps.put("userType", MapUtils.getString(map, "userType"));
                    maps.put("created", MapUtils.getString(map, "created"));
                    maps.put("expiredDate", MapUtils.getString(map, "expiredDate"));
                    activeListMap.add(maps);
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


    /**
     * 用户信息列表
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(Map<String, Object> param) {
        String loginTime = getLoginTime();
        param.put("loginTime", loginTime);
        Map<String, Integer> userTypeMap = MongodbCommon.getUserType();
        List<Map<String, Object>> list = sysUserInfoMapper.queryUserInfoList(param);
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
        String userType = MapUtils.getString(param, "userType");
        if (StringUtil.isNotEmpty(userType)) {
            List<Map<String, Object>> listMap = list;
            List<Map<String, Object>> activeListMap = new ArrayList<>();
            for (Map<String, Object> map : listMap) {
                String userType1 = MapUtils.getString(map, "userType");
                Map<String, Object> maps = new HashMap<>();
                if (userType.equals(userType1)) {
                    maps.put("pkid", MapUtils.getString(map, "pkid"));
                    maps.put("phoneNo", MapUtils.getString(map, "phoneNo"));
                    maps.put("loginName", MapUtils.getString(map, "loginName"));
                    maps.put("inCompany", MapUtils.getString(map, "inCompany"));
                    maps.put("userType", MapUtils.getString(map, "userType"));
                    maps.put("created", MapUtils.getString(map, "created"));
                    maps.put("expiredDate", MapUtils.getString(map, "expiredDate"));
                    activeListMap.add(maps);
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

    /**
     * 获取用户统计
     *
     * @return
     */
    @Override
    public Map<String, Object> getUserCount() {
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
        Map<String, Object> map = sysUserInfoMapper.queryUserInfoCount(param);
        try {
            Map<String, Integer> userPayCount = MongodbCommon.getUserPayCount();
            map.put("yesterdayPay", MapUtils.getInteger(userPayCount, "yesterdayPay"));
            map.put("todayPay", MapUtils.getInteger(userPayCount, "todayPay"));
            map.put("totalPayUser", MapUtils.getInteger(userPayCount, "totalPayUser"));
            Map<String, Object> pastDue = MongodbCommon.getPastDue();
            List<Map<String, Object>> list = sysUserInfoMapper.queryPast();
            int pastCount = 0;
            for (Map<String, Object> stringObjectMap : list) {
                String pkid = MapUtils.getString(pastDue, MapUtils.getString(stringObjectMap, "pkid"));
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

    /**
     * 个人信息
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getSingleUserInfo(Map<String, Object> param) {
        Map<String, Object> map = sysUserInfoMapper.querySingleUserInfo(param);
        String inviterCode = MapUtils.getString(map, "inviterCode");
        param.put("inviterCode",inviterCode);
        String phone = sysUserInfoMapper.queryInviterPhone(param);
        if(StringUtil.isNotEmpty(phone)){
            map.put("inviterPhone",phone);
        }
        return sysUserInfoMapper.querySingleUserInfo(param);
    }

    /**
     * 编辑备注
     * @param param
     */
    @Override
    public void updateRemark(Map<String, Object> param) {
        sysUserInfoMapper.updateRemark(param);
    }

    /**
     * 邀请人信息
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getInviterInfo(Map<String, Object> param) {
        Map<String, Integer> userTypeMap = MongodbCommon.getUserType();
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
        Map<String,Object> resultMap = new HashMap<>();
        int count = 0;
        if(list.size() > 0){
            count = list.size();
        }
        resultMap.put("list",list);
        resultMap.put("total",count);

        return resultMap;
    }

    /**
     * 订单统计
     * @return
     */
    @Override
    public Map<String, Object> getOrderCount() {
        return MongodbCommon.getOrderCount();
    }

    @Override
    public Map<String, Integer> getUserInfoType() {
        return MongodbCommon.getUserType();
    }



    public String getLoginTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //过去一月
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }


}
