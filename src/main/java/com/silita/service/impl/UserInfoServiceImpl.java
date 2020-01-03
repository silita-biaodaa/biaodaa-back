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
        String phone = sysUserInfoMapper.queryPhoneSingle(param);//根据id获取手机号码
        param.put("operand", phone);
        logsMapper.insertLogs(param);//添加操作日志
        sysUserInfoMapper.lockUser(param);//锁定用户
    }

    /**
     * 查询列表
     *
     * @param userInfo
     * @return
     */
    @Override
    public Map<String, Object> listUserInfo(SysUserInfo userInfo) {
        Integer total = sysUserInfoMapper.queryUserTotal(userInfo);//获取用户数量
        if (total <= 0) {
            return new HashMap<>();
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("list", sysUserInfoMapper.queryUserList(userInfo));//获取用户列表
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
        return sysUserInfoMapper.queryActiveUserCount(param);//获取活跃用户统计
    }


    /**
     * 获取活跃用户列表
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getActiveUserList(Map<String, Object> param) {
        mongodbUtils.isNull(param);//判断传值是否为空
        Map<String, Integer> userTypeMap = mongodbUtils.getUserType();//获取用户状态 ：统计次数为 1:付费、统计次数 2及以上：续费 没有 则是 注册
        List<Map<String, Object>> list = sysUserInfoMapper.queryActiveUserList(param);//获取活跃用户
        String userType = MapUtils.getString(param, "userType");
        try {
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {//遍历用户类型
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
        mongodbUtils.isNull(param);//判断传值是否为空
        Map<String, Integer> userTypeMap = mongodbUtils.getUserType();//获取用户状态 ：统计次数为 1:付费、统计次数 2及以上：续费 没有 则是 注册
        List<Map<String, Object>> list = sysUserInfoMapper.queryUserInfoList(param);//用户列表
        String userType = MapUtils.getString(param, "userType");
        try {
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {//遍历用户类型
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
        Map<String, Object> map = sysUserInfoMapper.queryUserInfoCount(param);//获取用户统计
        try {
            Map<String, Integer> userPayCount = mongodbUtils.getUserPayCount();//用户统计  统计今日/昨日  付费用户 及 总付费用户 和 付费用户
            List<Map<String, Object>> userPayCounts = mongodbUtils.getUserPayCounts();//总付费用户
            param.put("listUserId", userPayCounts);
            Map<String, Object> maps = new HashMap<>();
            List<Map<String, Object>> lists = sysUserInfoMapper.queryPhones(param);//根据用户id获取id和手机号码
            for (Map<String, Object> userId : lists) {
                maps.put(MapUtils.getString(userId, "pkid"), MapUtils.getString(userId, "phoneNo"));
            }
            int count = 0;
            for (Map<String, Object> payCount : userPayCounts) {//统计总付费用户
                String phone = MapUtils.getString(maps, MapUtils.getString(payCount, "userId"));
                if (StringUtil.isNotEmpty(phone)) {
                    count++;
                }
            }
            map.put("yesterdayPay", MapUtils.getInteger(userPayCount, "yesterdayPay"));//昨日付费
            map.put("todayPay", MapUtils.getInteger(userPayCount, "todayPay"));//今日付费
            map.put("totalPayUser", count);//总付费
            Map<String, Integer> pastDue = mongodbUtils.getUserType();//获取用户状态 ：统计次数为 1:付费、统计次数 2及以上：续费 没有 则是 注册
            List<Map<String, Object>> list = sysUserInfoMapper.queryPast();//获取id及对应的过期时间
            int pastCount = 0;
            for (Map<String, Object> stringObjectMap : list) {//统计过期用户
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
        Map<String, Object> map = sysUserInfoMapper.querySingleUserInfo(param);//获取用户个人信息
        String inviterCode = MapUtils.getString(map, "inviterCode");
        param.put("inviterCode", inviterCode);
        String phone = sysUserInfoMapper.queryInviterPhone(param);//获取邀请人手机号
        if (StringUtil.isNotEmpty(phone)) {
            map.put("inviterPhone", phone);
        }
        return sysUserInfoMapper.querySingleUserInfo(param);//获取用户个人信息
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
        String phone = sysUserInfoMapper.queryPhoneSingle(param);//查询单个手机号码
        param.put("operand", phone);
        logsMapper.insertLogs(param);//添加操作日志
        sysUserInfoMapper.updateRemark(param);//修改备注
    }

    /**
     * 被邀请人信息
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getInviterInfo(Map<String, Object> param) {
        Map<String, Integer> userTypeMap = mongodbUtils.getUserType();//获取用户类型
        List<Map<String, Object>> list = sysUserInfoMapper.queryInviterInfo(param);//根据邀请人码获取被邀请人信息
        try {
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {//遍历用户类型
                    map.put("created", MapUtils.getString(map, "created"));
                    Integer integer = userTypeMap.get(MapUtils.getString(map, "pkid"));
                    UserTypeCommon.judge(integer, map);
                }
            }
        } catch (Exception e) {
            logger.error("被邀请人信息", e);
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

    /**
     * 分页
     *
     * @param param
     * @param list
     * @param userType
     * @return
     */
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
