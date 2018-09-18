package com.silita.service.impl;

import com.silita.dao.TbNtAssociateGpMapper;
import com.silita.dao.TbNtTendersMapper;
import com.silita.model.TbNtAssociateGp;
import com.silita.model.TbNtMian;
import com.silita.model.TbNtTenders;
import com.silita.service.INoticeZhongBiaoService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-09-17 16:29
 */
@Service("noticeZhongBiaoService")
public class NoticeZhongBiaoServiceImpl extends AbstractService implements INoticeZhongBiaoService {

    @Autowired
    TbNtTendersMapper tbNtTendersMapper;
    @Autowired
    TbNtAssociateGpMapper tbNtAssociateGpMapper;

    @Override
    public List<TbNtTenders> listNtTenders(TbNtMian tbNtMian) {
        // 1、根据中标公告id获取关联的招标公告id
        TbNtAssociateGp tbNtAssociateGp = new TbNtAssociateGp();
        tbNtAssociateGp.setNtId(tbNtMian.getPkid());
        tbNtAssociateGp.setSource(tbNtMian.getSource());
        tbNtAssociateGp.setTableName(DataHandlingUtil.SplicingTable(tbNtAssociateGp.getClass(), tbNtAssociateGp.getSource()));
        String NtId = tbNtAssociateGpMapper.getNtIdByNtId(tbNtAssociateGp);
        // 2、根据招标公告id获取招标标段信息
        TbNtTenders tbNtTenders = new TbNtTenders();
        tbNtTenders.setNtId(NtId);
        tbNtTenders.setSource(tbNtMian.getSource());
        tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
        return tbNtTendersMapper.listNtTendersByNtId(tbNtTenders);
    }

    @Override
    public Map<String, Object> listNtAssociateGp(TbNtMian tbNtMian) {
        TbNtAssociateGp tbNtAssociateGp = new TbNtAssociateGp();
        tbNtAssociateGp.setNtId(tbNtMian.getPkid());
        tbNtAssociateGp.setSource(tbNtMian.getSource());
        tbNtAssociateGp.setTableName(DataHandlingUtil.SplicingTable(TbNtAssociateGp.class, tbNtAssociateGp.getSource()));
        Map result = new HashMap<String, Object>();
        result.put("datas", tbNtAssociateGpMapper.listNtAssociateGpByNtId(tbNtAssociateGp));
        result.put("total", tbNtAssociateGpMapper.countNtAssociateGpByNtId(tbNtAssociateGp));
        return super.handlePageCount(result, tbNtAssociateGp);
    }


}
