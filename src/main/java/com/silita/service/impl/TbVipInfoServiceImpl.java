package com.silita.service.impl;

import com.silita.dao.SysUserInfoMapper;
import com.silita.dao.TbVipInfoMapper;
import com.silita.dao.TbVipProfitsMapper;
import com.silita.model.SysUserInfo;
import com.silita.service.ITbVipInfoService;
import com.silita.utils.DataHandlingUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.silita.biaodaa.elastic.Enum.FieldType.integer;

@Service
public class TbVipInfoServiceImpl implements ITbVipInfoService {
    @Autowired
    private TbVipInfoMapper tbVipInfoMapper;
    @Autowired
    private TbVipProfitsMapper tbVipProfitsMapper;


    /**
     * 新增 || 编辑  会员信息
     *
     * @param param
     */
    @Override
    public void addVipInfo(Map<String, Object> param) {
        Integer vipDay = MapUtils.getInteger(param, "vipDay");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
