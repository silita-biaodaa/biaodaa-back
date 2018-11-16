package com.silita.service;

import com.silita.model.TbNtMian;

import java.util.Map;

public interface INoticeService {

    /**
     * 添加公告
     * @param mian
     */
    Map<String,Object> addNotice(TbNtMian mian);
}
