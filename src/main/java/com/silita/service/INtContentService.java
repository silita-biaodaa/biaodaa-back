package com.silita.service;

import com.silita.model.TbNtText;

import java.io.IOException;
import java.util.Map;

/**
 * tb_nt_text service
 */
public interface INtContentService {


    /**
     * 获取公告原文
     * @param param
     * @return
     * @throws IOException
     */
    String queryCentent(Map<String,Object> param) throws IOException;
}
