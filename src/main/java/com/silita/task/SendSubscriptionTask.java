package com.silita.task;

import com.silita.common.RegionCommon;
import com.silita.dao.TbNtMianMapper;
import com.silita.dao.TbNtSiteMapper;
import com.silita.dao.TbSiteCountMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendSubscriptionTask implements Runnable {
    private static final Logger logger = Logger.getLogger(SendSubscriptionTask.class);

    private TbNtSiteMapper tbNtSiteMapper;
    private TbNtMianMapper tbNtMianMapper;
    private TbSiteCountMapper tbSiteCountMapper;

    public SendSubscriptionTask(TbNtSiteMapper tbNtSiteMapper, TbNtMianMapper tbNtMianMapper, TbSiteCountMapper tbSiteCountMapper) {
        this.tbNtSiteMapper = tbNtSiteMapper;
        this.tbNtMianMapper = tbNtMianMapper;
        this.tbSiteCountMapper = tbSiteCountMapper;
    }

    @Override
    public void run() {
        try {
            Map<String, Object> param = new HashMap<>();
            //Map<String, String> regionSource = RegionCommon.regionSource;
            //for (String source : regionSource.keySet()) {
            param.put("source", "hunan");
            List<String> list = tbNtMianMapper.queryNoticeSitePubDate(param);//获取地区时间
            for (String s : list) {
                param.put("startDate", s);
                param.put("pubDate", s);
                param.put("sourced", RegionCommon.regionSource.get("hunan"));
                List<Map<String, Object>> list1 = tbNtSiteMapper.querySiteUtl(param);
                for (Map<String, Object> map : list1) {
                    String name = MapUtils.getString(map, "name");
                    siteCounts(map, param, name);
                }
                siteCount(list1);
                param.put("list1", list1);
                tbSiteCountMapper.insertSiteCount(param);
            }
            //}
        } catch (Exception e) {
            logger.error("获取公告站点异常", e);
        }
    }


    public void siteCounts(Map<String, Object> map, Map<String, Object> param, String name) {

        List<Map<String, Object>> list = tbNtMianMapper.querySiteNoticeCounts(param);
        if (null != list && list.size() > 0) {
            for (Map<String, Object> stringObjectMap : list) {
                String srcSite = MapUtils.getString(stringObjectMap, "srcSite");
                if (name.equals(srcSite)) {
                    Integer siteCount = MapUtils.getInteger(stringObjectMap, "siteCount");
                    map.put("siteCount", siteCount);
                }
            }
        }

    }

    public void siteCount(List<Map<String, Object>> list1) {
        for (Map<String, Object> map : list1) {
            Integer siteCount = MapUtils.getInteger(map, "siteCount");
            if (null == siteCount) {
                map.put("siteCount", 0);
            }
        }
    }
}
