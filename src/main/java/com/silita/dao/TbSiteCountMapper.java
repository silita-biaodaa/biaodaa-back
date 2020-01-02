package com.silita.dao;

import com.silita.model.TbSiteCount;
import com.silita.utils.MyMapper;

import java.util.Map;

public interface TbSiteCountMapper extends MyMapper<TbSiteCount> {

    /**
     * 添加站点统计数据
     * @param param
     */
    void insertSiteCount(Map<String,Object> param);

}