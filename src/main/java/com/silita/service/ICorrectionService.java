package com.silita.service;

import com.silita.model.Snatchurl;

import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-10-18 14:26
 */
public interface ICorrectionService {

    /**
     * 根据查询条件获取招、中标纠错列表
     * @return
     */
    Map<String, Object> listSnatchurl(Snatchurl snatchurl);

    /**
     * 根据公告pkid更新公告显示状态
     * @param snatchurl
     * @return
     */
    Integer updateSnatchurlIsShowById(Snatchurl snatchurl);
}
