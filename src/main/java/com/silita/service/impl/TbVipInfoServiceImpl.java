package com.silita.service.impl;

import com.silita.dao.*;
import com.silita.service.ITbVipInfoService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.oldProjectUtils.CommonUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Service
public class TbVipInfoServiceImpl implements ITbVipInfoService {
    @Autowired
    private TbVipInfoMapper tbVipInfoMapper;
    @Autowired
    private TbVipProfitsMapper tbVipProfitsMapper;
    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;
    @Autowired
    SysLogsMapper logsMapper;
    @Autowired
    TbMessageMapper tbMessageMapper;

    /**
     * 新增 || 编辑  会员信息
     *
     * @param param
     */
    @Override
    public void addVipInfo(Map<String, Object> param) {
        Integer vipDay = MapUtils.getInteger(param, "vipDay");
        String userId = MapUtils.getString(param, "userId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Map<String, Object> map = tbVipInfoMapper.queryVipInfoUserCount(param);
            if (map != null && map.size() > 0) {
                String expiredDate = MapUtils.getString(map, "expiredDate");
                param.put("vId",MapUtils.getString(map,"vId"));
                Date dates = sdf.parse(expiredDate);
                Calendar c = Calendar.getInstance();
                c.setTime(dates);
                c.add(Calendar.DATE, vipDay);
                String date = sdf.format(c.getTime());
                param.put("expiredDate",date);
                tbVipInfoMapper.updateVipIfo(param);
            } else {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, vipDay);
                String date = sdf.format(c.getTime());
                param.put("expiredDate",date);
                param.put("vId",DataHandlingUtil.getUUID());
                param.put("userId",MapUtils.getString(map,"userId"));
                tbVipInfoMapper.insertVipInfo(param);
            }
            param.put("vProfitsId",DataHandlingUtil.getUUID());
            tbVipProfitsMapper.insertVipProfits(param);
            //编辑用户状态  普通用户 -- 》 会员用户
            sysUserInfoMapper.updateUserState(param);
            param.put("pkid",MapUtils.getString(param, "userId"));
            param.put("pid", CommonUtil.getUUID());
            param.put("optType", "赠送会员");
            param.put("optDesc", "赠送"+vipDay+"天会员");
            String phone = sysUserInfoMapper.queryPhoneSingle(param);
            param.put("operand", phone);
            logsMapper.insertLogs(param);//添加操作日志
            param.put("userId",userId);
            param.put("msgTitle","赠送会员成功通知");
            param.put("msgType","system");
            param.put("msgContent","已赠送您"+vipDay+"天会员，请查收！");
            tbMessageMapper.insertMessage(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
