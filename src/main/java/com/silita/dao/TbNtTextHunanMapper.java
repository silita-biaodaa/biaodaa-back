package com.silita.dao;

import com.silita.model.TbNtText;
import com.silita.utils.MyMapper;

import java.util.Map;

public interface TbNtTextHunanMapper extends MyMapper<TbNtText> {

    /**
     * 获取公告内容
     * @param textHunan
     * @return
     */
    TbNtText queryNtTextDetail(TbNtText textHunan);

    /**
     * 获取爬取id
     * @param param
     * @return
     */
    String getSnatchid(Map<String,Object> param);

    /**
     * 修改公告原文
     * @param ntTextHunan
     * @return
     */
    Integer updateNtText(TbNtText ntTextHunan);

    /**
     * 添加公告原文
     * @param tbNtText
     * @return
     */
    int inertNtText(TbNtText tbNtText);
}