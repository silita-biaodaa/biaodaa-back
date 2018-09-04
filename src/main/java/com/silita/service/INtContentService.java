package com.silita.service;

import com.silita.model.TbNtText;

/**
 * tb_nt_text service
 */
public interface INtContentService {

    /**
     * 获取公告原文详情
     * @param ntText
     * @return
     */
    TbNtText getNtContent(TbNtText ntText);
}
