package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.TbNtMianMapper;
import com.silita.dao.TbNtTextHunanMapper;
import com.silita.model.TbNtMian;
import com.silita.model.TbNtText;
import com.silita.service.INoticeService;
import com.silita.utils.DataHandlingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NoticeServiceImpl implements INoticeService {

    @Autowired
    TbNtMianMapper tbNtMianMapper;
    @Autowired
    TbNtTextHunanMapper tbNtTextHunanMapper;

    @Override
    public Map<String, Object> addNotice(TbNtMian mian) {
        if (null == mian) {
            return new HashMap<>();
        }
        Map<String, Object> resultMap = new HashMap<>();
        mian.setTableName(DataHandlingUtil.SplicingTable(mian.getClass(), mian.getSource()));
        mian.setNtStatus("1");
        mian.setIsEnable("1");
        mian.setPkid(DataHandlingUtil.getUUID());
        int cunt = tbNtMianMapper.queryNtMainCount(mian);
        if (cunt > 0) {
            resultMap.put("code", Constant.CODE_WARN_400);
            resultMap.put("msg", Constant.MSG_WARN_400);
            return resultMap;
        }
        int count = tbNtMianMapper.insertNtMian(mian);
        if (count > 0) {
            TbNtText tbNtText = new TbNtText();
            tbNtText.setPkid(DataHandlingUtil.getUUID());
            tbNtText.setTableName(DataHandlingUtil.SplicingTable(tbNtText.getClass(), mian.getSource()));
            tbNtText.setContent(mian.getContent());
            tbNtText.setNtId(mian.getPkid());
            tbNtTextHunanMapper.inertNtText(tbNtText);
        }
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }
}
