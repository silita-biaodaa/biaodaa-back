package com.silita.service.impl;

import com.silita.dao.SysFilesMapper;
import com.silita.dao.TbNtAssociateGpMapper;
import com.silita.dao.TbNtChangeMapper;
import com.silita.dao.TbNtTendersMapper;
import com.silita.model.SysFiles;
import com.silita.model.TbNtAssociateGp;
import com.silita.model.TbNtMian;
import com.silita.model.TbNtTenders;
import com.silita.service.INoticeZhongBiaoService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    TbNtChangeMapper tbNtChangeMapper;
    @Autowired
    SysFilesMapper sysFilesMapper;

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
    public void deleteNtTendersByPkId(Map params) {
        String idStr = (String) params.get("idsStr");
        String source = (String) params.get("source");
        String tableName = DataHandlingUtil.SplicingTable(TbNtTenders.class, source);
        String[] ids = idStr.split("\\|");
        Set set = new HashSet<String>();
        for (String id : ids) {
            set.add(id);
        }
        if (set != null && set.size() > 0) {
            // 1、 删除变更信息
            tbNtChangeMapper.deleteTbNtChangeByNtEditId(set.toArray());
            // 2、删除编辑明细
            tbNtTendersMapper.deleteNtTendersByPkId(tableName, set.toArray());
        }
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

    @Override
    public List<SysFiles> listZhaoBiaoFilesByPkId(TbNtMian tbNtMian) {
        // 1、根据中标公告id获取关联的招标公告id
        TbNtAssociateGp tbNtAssociateGp = new TbNtAssociateGp();
        tbNtAssociateGp.setNtId(tbNtMian.getPkid());
        tbNtAssociateGp.setSource(tbNtMian.getSource());
        tbNtAssociateGp.setTableName(DataHandlingUtil.SplicingTable(tbNtAssociateGp.getClass(), tbNtAssociateGp.getSource()));
        String NtId = tbNtAssociateGpMapper.getNtIdByNtId(tbNtAssociateGp);
        // 2、根据招标公告id获取文件列表
        SysFiles sysFiles = new SysFiles();
        sysFiles.setBizId(NtId);
        sysFiles.setSource(tbNtMian.getSource());
        return sysFilesMapper.listSysFilesByBizId(sysFiles);
    }


}
