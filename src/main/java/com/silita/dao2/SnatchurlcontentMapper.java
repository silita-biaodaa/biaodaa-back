package com.silita.dao2;

import com.silita.model.Snatchurlcontent;
import com.silita.utils.MyMapper;

public interface SnatchurlcontentMapper extends MyMapper<Snatchurlcontent> {

    /**
     * 根据公告pkid获取公告内容
     * @return
     */
    Snatchurlcontent getSnatchurlcontentBySnatchUrlId(Snatchurlcontent snatchurlcontent);
}