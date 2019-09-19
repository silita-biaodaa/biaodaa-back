package com.silita.service.impl;

import com.silita.dao.SysUserInfoMapper;
import com.silita.dao.TbVipInfoMapper;
import com.silita.dao.TbVipProfitsMapper;
import com.silita.service.ITbVipProfitsService;
import com.silita.service.mongodb.MongodbService;
import com.silita.utils.dateUtils.MyDateUtils;
import com.sun.xml.internal.xsom.impl.scd.Iterators;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

@Service
public class TbVipProfitsServiceImpl implements ITbVipProfitsService {
    @Autowired
    private TbVipInfoMapper tbVipInfoMapper;
    @Autowired
    private TbVipProfitsMapper tbVipProfitsMapper;
    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;
    @Autowired
    private MongodbService mongodbUtils;

    /**
     * 会员明细
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> getVipProfitsSingle(Map<String, Object> param) {
        //订单
        List<Map<String, Object>> topUpListMap = mongodbUtils.getTopUp(param);
        if (topUpListMap != null && topUpListMap.size() > 0) {
            for (Map<String, Object> map : topUpListMap) {
                Integer vipDays = MapUtils.getInteger(map, "vipDays");
                String created = MapUtils.getString(map, "created");
                String tomorrowTime = MyDateUtils.getTomorrowTime(created, vipDays);
                map.put("expiredDate", tomorrowTime);
            }
        }
        //邀请人
        String ownInviteCode = sysUserInfoMapper.queryinviterCode(param);
        if (StringUtil.isNotEmpty(ownInviteCode)) {
            param.put("ownInviteCode", ownInviteCode);
            List<Map<String, Object>> listInviterCode = tbVipProfitsMapper.queryVipProfitsInviter(param);
            if (listInviterCode != null && listInviterCode.size() > 0) {
                for (Map<String, Object> map : listInviterCode) {
                    String created = MyDateUtils.strToDates(MapUtils.getString(map, "created"), "yyyy-MM-dd HH:mm:ss");
                    map.put("created", created);
                    map.put("behavior", "邀请新用户" + MapUtils.getString(map, "behavior"));
                    map.put("vipDay", "邀请赠送" + MapUtils.getInteger(map, "vipDay") + "天会员");
                }
                topUpListMap.addAll(listInviterCode);
            }
        }
        String vId = tbVipInfoMapper.queryVipInfoSingle(param);
        param.put("vId", vId);
        List<Map<String, Object>> list = tbVipProfitsMapper.queryVipProfitsSingle(param);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                String created = MyDateUtils.strToDates(MapUtils.getString(map, "created"), "yyyy-MM-dd HH:mm:ss");
                map.put("created", created);
                String settingsCode = MapUtils.getString(map, "settingsCode");
                if (settingsCode.equals("a-first")) {
                    map.put("behavior", "注册");
                    map.put("vipDay", "注册赠送" + MapUtils.getInteger(map, "vipDay") + "天会员");
                } else if (settingsCode.equals("a-operate")) {
                    map.put("behavior", "标大大赠送");
                    map.put("vipDay", "标大大赠送" + MapUtils.getInteger(map, "vipDay") + "天会员");
                }else if(settingsCode.equals("a-binding")){
                    map.put("behavior","关注公众号并绑定赠送");
                    map.put("vipDay","关注公众号并绑定赠送" + MapUtils.getInteger(map,"vipDay") + "天会员");
                }else if(settingsCode.equals("a-isinvite")){
                    map.put("behavior","被邀请");
                    map.put("vipDay","被邀请赠送" + MapUtils.getInteger(map,"vipDay") + "天会员");
                }
            }
            topUpListMap.addAll(list);
        }

        if (topUpListMap != null && topUpListMap.size() > 1) {
            Collections.sort(topUpListMap, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    String name1 = (String) o1.get("created");//name1是从你list里面拿出来的一个
                    String name2 = (String) o2.get("created"); //name1是从你list里面拿出来的第二个name
                    return name2.compareTo(name1);
                }
            });
        }

        return topUpListMap;
    }
}
