package com.silita.service.impl;

import com.silita.dao.*;
import com.silita.model.*;
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
    @Autowired
    TbNtBidsMapper tbNtBidsMapper;
    @Autowired
    TbNtMianMapper tbNtMianMapper;
    @Autowired
    TbNtBidsCandMapper tbNtBidsCandMapper;

    @Override
    public List<TbNtTenders> listNtTenders(TbNtMian tbNtMian) {
        List<TbNtTenders> lists = null;
        // 1、根据中标公告id获取关联的招标公告id
        TbNtAssociateGp tbNtAssociateGp = new TbNtAssociateGp();
        tbNtAssociateGp.setNtId(tbNtMian.getPkid());
        tbNtAssociateGp.setSource(tbNtMian.getSource());
        tbNtAssociateGp.setTableName(DataHandlingUtil.SplicingTable(tbNtAssociateGp.getClass(), tbNtAssociateGp.getSource()));
        List<String> ntIds = tbNtAssociateGpMapper.getNtIdByNtId(tbNtAssociateGp);
        if(null != ntIds && ntIds.size() > 0) {
            lists = new ArrayList<TbNtTenders>(ntIds.size());
            for (int i = 0; i < ntIds.size(); i++) {
                String ntId = ntIds.get(i);
                TbNtTenders tbNtTenders = new TbNtTenders();
                tbNtTenders.setNtId(ntId);
                tbNtTenders.setSource(tbNtMian.getSource());
                tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
                //判断公告是否存在招标编辑明细
                Integer count = tbNtTendersMapper.countNtTendersByNtId(tbNtTenders);
                if(count > 0) {
                    // 2、根据招标公告id获取招标标段信息
                    lists.addAll(tbNtTendersMapper.listNtTendersByNtId(tbNtTenders));
                }
            }
        }
      return lists;
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
            TbNtTenders tbNtTenders = new TbNtTenders();
            tbNtTenders.setPkid(ids[0]);
            tbNtTenders.setSource(source);
            tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
            //获取公告pkid用于判断公共是否还有标段信息
            String ntId = tbNtTendersMapper.getNtIdByNtId(tbNtTenders);
            // 1、删除变更信息
            tbNtChangeMapper.deleteTbNtChangeByNtEditId(set.toArray());
            // 2、删除招标编辑明细
            tbNtTendersMapper.deleteNtTendersByPkId(tableName, set.toArray());
            // 3、获取招标编辑明细个数
            tbNtTenders.setNtId(ntId);
            Integer count = tbNtTendersMapper.countNtTendersByNtId(tbNtTenders);
            if(count == 0) {
                TbNtMian tbNtMian = new TbNtMian();
                tbNtMian.setPkid(ntId);
                tbNtMian.setNtStatus("0");
                tbNtMian.setSource(source);
                tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), tbNtMian.getSource()));
                //更新公告状态为 新建
                tbNtMianMapper.updateCategoryAndStatusByPkId(tbNtMian);
            }
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
        List<SysFiles> lists = null;
        // 1、根据中标公告id获取关联的招标公告id
        TbNtAssociateGp tbNtAssociateGp = new TbNtAssociateGp();
        tbNtAssociateGp.setNtId(tbNtMian.getPkid());
        tbNtAssociateGp.setSource(tbNtMian.getSource());
        tbNtAssociateGp.setTableName(DataHandlingUtil.SplicingTable(tbNtAssociateGp.getClass(), tbNtAssociateGp.getSource()));
        List<String> ntIds = tbNtAssociateGpMapper.getNtIdByNtId(tbNtAssociateGp);
        if(null != ntIds && ntIds.size() > 0) {
            lists = new ArrayList<SysFiles>(ntIds.size());
            for (int i = 0; i < ntIds.size(); i++) {
                String ntId = ntIds.get(i);
                SysFiles sysFiles = new SysFiles();
                sysFiles.setBizId(ntId);
                sysFiles.setSource(tbNtMian.getSource());
                //判断公告是否存在招标文件列表
                Integer count = sysFilesMapper.countSysFilesByBizIdAndSource(sysFiles);
                if(count > 0) {
                    //2、根据招标公告id获取文件列表
                    lists.addAll(sysFilesMapper.listSysFilesByBizId(sysFiles));
                }
            }
        }
        return lists;
    }

    public String saveNtBids(TbNtBids tbNtBids) {
        String msg = null;
        //更新招标主表状态
        TbNtMian tbNtMian = new TbNtMian();
        tbNtMian.setPkid(tbNtBids.getNtId());
        tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), tbNtBids.getSource()));
        tbNtMian.setBinessType(tbNtBids.getBinessType());
        tbNtMian.setTitle(tbNtBids.getTitle());
        tbNtMian.setPubDate(tbNtBids.getPubDate());
        tbNtMian.setCityCode(tbNtBids.getCityCode());
        tbNtMian.setCountyCode(tbNtBids.getCountyCode());
        tbNtMianMapper.updateNtMainByPkId(tbNtMian);
        //更新招标标段表信息
        TbNtTenders tbNtTenders = new TbNtTenders();
        tbNtTenders.setNtId(tbNtBids.getNtId());
        tbNtTenders.setEditCode(tbNtBids.getTdEditCode());
        tbNtTenders.setProType(tbNtBids.getProType());
        tbNtTenders.setPbMode(tbNtBids.getPbMode());
        tbNtTenders.setSource(tbNtBids.getSource());
        tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtBids.getSource()));
        tbNtTendersMapper.updateProTypeAndPbModeByNtIdAndEditCode(tbNtTenders);
        //中标标段
        tbNtBids.setTableName(DataHandlingUtil.SplicingTable(tbNtBids.getClass(), tbNtBids.getSource()));
        Integer count = tbNtBidsMapper.countNtBidsByNtIdAndSegment(tbNtBids);
        if(count == 0) {
            //添加中标标段基本信息
            tbNtBids.setPkid(DataHandlingUtil.getUUID());
            tbNtBids.setEditCode("td" + System.currentTimeMillis() + DataHandlingUtil.getNumberRandom(2));
            tbNtBidsMapper.insertTbNtBids(tbNtBids);
            //批量添加中标候选人
            List<TbNtBidsCand> tbNtBidsCands = tbNtBids.getBidsCands();
            if(null != tbNtBidsCands && tbNtBidsCands.size() > 0) {
                for (int i = 0; i < tbNtBidsCands.size(); i++) {
                    tbNtBidsCands.get(i).setPkid(DataHandlingUtil.getUUID());
                    tbNtBidsCands.get(i).setNtBidsId(tbNtBids.getPkid());
                    tbNtBidsCands.get(i).setCreateBy(tbNtBids.getCreateBy());
                }
                tbNtBidsCandMapper.batchInsertNtBidsCand(tbNtBidsCands);
            }
            msg = "添加标段信息成功！";
        } else {
            //更新中标标段基本信息
            tbNtBidsMapper.updateTbNtBidsByNtIdAndSegment(tbNtBids);
            //批量更新中标候选人
            List<TbNtBidsCand> tbNtBidsCands = tbNtBids.getBidsCands();
            if(null != tbNtBidsCands && tbNtBidsCands.size() > 0) {
                tbNtBidsCandMapper.batchUpdateNtBidsCand(tbNtBidsCands);
            }
            msg = "更新标段信息成功！";
        }
        return msg;
    }

    @Override
    public List<TbNtBids> listTbNtBidsByNtId(TbNtBids tbNtBids) {
        return tbNtBidsMapper.listNtBidsByNtId(tbNtBids);
    }

}
