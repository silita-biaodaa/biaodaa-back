package com.silita.service.impl;

import com.silita.dao.TbNtMianMapper;
import com.silita.dao.TbNtRecycleHunanMapper;
import com.silita.model.TbNtMian;
import com.silita.model.TbNtRecycle;
import com.silita.service.IRecycleService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * tb_nt_recycle serviceimpl
 */
@Service
public class RecycleServiceImpl extends AbstractService implements IRecycleService {

    @Autowired
    TbNtRecycleHunanMapper tbNtRecycleHunanMapper;
    @Autowired
    TbNtMianMapper tbNtMianMapper;

    @Override
    public Map<String, Object> getRecycleList(TbNtRecycle recycle) {
        Map<String,Object> param = new HashMap<>();
        param.put("list",tbNtRecycleHunanMapper.queryNtRecycleList(recycle));
        param.put("total",tbNtRecycleHunanMapper.queryNtRecycleTotal(recycle));
        return super.handlePageCount(param,recycle);
    }

    @Override
    public void delRecycel(Map<String, Object> param) {
        String[] pkids = MapUtils.getString(param,"pkids").split("\\|");
        TbNtRecycle recycle = new TbNtRecycle();
        recycle.setSource(MapUtils.getString(param,"source"));
        for (String id :pkids){
            recycle.setPkid(id);
            tbNtRecycleHunanMapper.deleteRecyleLogic(recycle);//删除回收站公告（逻辑删除）
        }
    }

    @Override
    public void recoverRecycle(Map<String, Object> param) {
        String[] pkids = MapUtils.getString(param,"pkids").split("\\|");
        TbNtRecycle recycle = new TbNtRecycle();
        TbNtRecycle ntRecycle = null;
        recycle.setSource(MapUtils.getString(param,"source"));
        TbNtMian mian;
        for (String id :pkids){
            recycle.setPkid(id);
            ntRecycle = tbNtRecycleHunanMapper.queryNtRecycleDetail(recycle);//查询回收站公告详情
            tbNtRecycleHunanMapper.deleteRecyleLogic(ntRecycle);//删除回收站公告（逻辑删除）
            mian = new TbNtMian();
            mian.setTableName(DataHandlingUtil.SplicingTable(mian.getClass(),ntRecycle.getSource()));
            mian.setPkid(ntRecycle.getNtId());
            mian.setUpdateBy(MapUtils.getString(param,"username"));
            mian.setIsEnable("1");
            tbNtMianMapper.deleteNtMainByPkId(mian);//根据主键删除公告id
        }
    }
}
