package com.silita.service.impl;

import com.silita.dao.TbNtRecycleHunanMapper;
import com.silita.model.TbNtRecycle;
import com.silita.service.IRecycleService;
import com.silita.service.abs.AbstractService;
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
            tbNtRecycleHunanMapper.deleteRecyleLogic(recycle);
        }
    }

    @Override
    public void recoverRecycle(Map<String, Object> param) {
        String[] pkids = MapUtils.getString(param,"pkids").split("\\|");
        TbNtRecycle recycle = new TbNtRecycle();
        TbNtRecycle ntRecycle = null;
        recycle.setSource(MapUtils.getString(param,"source"));
        for (String id :pkids){
            recycle.setPkid(id);
            ntRecycle = tbNtRecycleHunanMapper.queryNtRecycleDetail(recycle);
            tbNtRecycleHunanMapper.deleteRecyleLogic(recycle);
        }
    }
}
