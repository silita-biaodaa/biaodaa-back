package com.silita.service.impl;

import com.silita.dao.*;
import com.silita.model.DicCommon;
import com.silita.model.SysArea;
import com.silita.model.TbNtMian;
import com.silita.model.TbNtTenders;
import com.silita.service.INoticeZhaoBiaoService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-27 16:52
 */
@Service("noticeZhaoBiaoService")
public class NoticeZhaoBiaoServiceImpl extends AbstractService implements INoticeZhaoBiaoService {

    @Autowired
    DicCommonMapper dicCommonMapper;
    @Autowired
    TwfDictMapper twfDictMapper;
    @Autowired
    TbNtMianMapper tbNtMianMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;
    @Autowired
    TbNtTendersMapper tbNtTendersMapper;

    @Override
    @Cacheable(value="TwfDictNameCache")
    public Map<String, String> listFixedEditData() {
        Map result = new HashMap<String, Object>();
        result.put("bidOpeningPersonnel", twfDictMapper.listTwfDictNameByType(3));
        result.put("projectType", twfDictMapper.listTwfDictNameByType(4));
        result.put("biddingType", twfDictMapper.listTwfDictNameByType(5));
        result.put("filingRequirements", twfDictMapper.listTwfDictNameByType(6));
        result.put("biddingStatus", twfDictMapper.listTwfDictNameByType(7));
        return result;
    }

    @Override
    public Map<String, Object> listBulletinStatus() {
        return DataHandlingUtil.getBulletinStatus();
    }

    @Override
    public List<String> listDicCommonNameByType(DicCommon dicCommon) {
        String type = dicCommon.getType();
        dicCommon.setType(type + "_pbmode");
        return dicCommonMapper.listDicCommonNameByType(dicCommon);
    }

    @Override
    public List<Map<String, Object>> listSysAreaByParentId(SysArea sysArea) {
        return sysAreaMapper.listSysAreaByParentId(sysArea.getAreaParentId());
    }

    @Override
    public Map<String, Object> listNtMain(TbNtMian tbNtMian) {
        tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), tbNtMian.getSource()));
        Map result = new HashMap<String, Object>();
        result.put("datas", tbNtMianMapper.listNtMain(tbNtMian));
        result.put("total", tbNtMianMapper.countNtMian(tbNtMian));
        return super.handlePageCount(result, tbNtMian);
    }

    @Override
    public void updateNtMainStatus(TbNtMian tbNtMian) {
        tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), tbNtMian.getSource()));
        tbNtMianMapper.updateNtMainCategoryAndStatusByPkId(tbNtMian);
    }

    @Override
    public String insertNtTenders(TbNtTenders tbNtTenders) {
        String msg = "";
        tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
        Integer count = tbNtTendersMapper.countNtTendersByNtIdAndSegment(tbNtTenders);
        if(count == 0) {
            tbNtTendersMapper.insertNtTenders(tbNtTenders);
            msg = "添加标段成功！";
        }
        return msg;
    }

    @Override
    public void updateNtTenders(TbNtTenders tbNtTenders) {
        tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
        tbNtTendersMapper.updateNtTendersByPkId(tbNtTenders);
    }

}
