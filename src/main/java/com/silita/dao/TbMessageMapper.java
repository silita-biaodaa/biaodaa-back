package com.silita.dao;

import com.silita.model.TbMessage;
import com.silita.utils.MyMapper;

import java.util.Map;

public interface TbMessageMapper extends MyMapper<TbMessage> {
    /**
     * 添加消息
     * @param param
     */
    void insertMessage(Map<String,Object> param);
}