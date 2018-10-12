package com.silita.dao;

import com.silita.model.TbNtRegexQua;
import com.silita.utils.MyMapper;

public interface TbNtRegexQuaMapper extends MyMapper<TbNtRegexQua> {

    /**
     * 添加公告资质表达式关系表
     * @return
     */
    Integer insertTbNtRegexQua(TbNtRegexQua tbNtRegexQua);
}