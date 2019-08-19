package com.silita.service.impl;

import com.silita.common.MongodbCommon;
import com.silita.dao.SysUserInfoMapper;
import com.silita.dao.TbVipInfoMapper;
import com.silita.dao.TbVipProfitsMapper;
import com.silita.service.ITbVipProfitsService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class TbVipProfitsServiceImpl implements ITbVipProfitsService {
    @Autowired
    private TbVipInfoMapper tbVipInfoMapper;
    @Autowired
    private TbVipProfitsMapper tbVipProfitsMapper;
    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;

    /**
     * 会员明细
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> getVipProfitsSingle(Map<String, Object> param) {
        //订单
        List<Map<String, Object>> topUpListMap = MongodbCommon.getTopUp(param);
        if (topUpListMap != null && topUpListMap.size() >0) {
            for (Map<String, Object> map : topUpListMap) {
                String date = tbVipInfoMapper.queryDate(param);
                map.put("expiredDate", date);
            }
        }
        //邀请人
        String inviterCode = sysUserInfoMapper.queryinviterCode(param);
        if (StringUtil.isNotEmpty(inviterCode)) {
            param.put("inviterCode", inviterCode);
            List<Map<String, Object>> listInviterCode = tbVipProfitsMapper.queryVipProfitsInviter(param);
            if (listInviterCode != null && listInviterCode.size() > 0) {
                for (Map<String, Object> map : listInviterCode) {
                    map.put("created", MapUtils.getString(map, "created"));
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
                map.put("created", MapUtils.getString(map, "created"));
                String settingsCode = MapUtils.getString(map, "settingsCode");
                if (settingsCode.equals("a-first")) {
                    map.put("behavior", "注册");
                    map.put("vipDay", "注册赠送" + MapUtils.getInteger(map, "vipDay") + "天会员");
                } else if (settingsCode.equals("a-operate")) {
                    map.put("behavior", "标大大赠送");
                    map.put("vipDay", "标大大赠送" + MapUtils.getInteger(map, "vipDay") + "天会员");
                }
            }
            topUpListMap.addAll(list);
        }

        return topUpListMap;
    }
}
