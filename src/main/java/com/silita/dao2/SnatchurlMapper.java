package com.silita.dao2;

import com.silita.model.Snatchurl;
import com.silita.utils.MyMapper;

import java.util.List;

public interface SnatchurlMapper extends MyMapper<Snatchurl> {

    /**
     * 根据查询条件获取招、中标纠错列表
     * @param snatchurl
     * @return
     */
    List<Snatchurl> listSnatchurl(Snatchurl snatchurl);

    /**
     * 根据查询条件获取招、中标纠错列表数据条数
     * @param snatchurl
     * @return
     */
    Integer countSnatchurl(Snatchurl snatchurl);

    /**
     * 根据公告pkid更新公告显示状态
     * @param snatchurl
     * @return
     */
    Integer updateSnatchurlIsShowById(Snatchurl snatchurl);
}