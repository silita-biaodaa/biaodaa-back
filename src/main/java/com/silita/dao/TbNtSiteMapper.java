package com.silita.dao;

import com.silita.model.TbNtSite;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface TbNtSiteMapper extends MyMapper<TbNtSite> {

    /**
     * 获取公告站点地址
     * @return
     */
    List<Map<String,Object>> querySiteUtl();
}