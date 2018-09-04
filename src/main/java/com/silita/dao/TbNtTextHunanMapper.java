package com.silita.dao;

import com.silita.model.TbNtText;
import com.silita.utils.MyMapper;

public interface TbNtTextHunanMapper extends MyMapper<TbNtText> {

    /**
     * 获取公告内容
     * @param textHunan
     * @return
     */
    TbNtText queryNtTextDetail(TbNtText textHunan);

    /**
     * 修改公告原文
     * @param ntTextHunan
     * @return
     */
    Integer updateNtText(TbNtText ntTextHunan);
}