package com.silita.dao;

import com.silita.model.TbNtRegexQua;
import com.silita.utils.MyMapper;

public interface TbNtRegexQuaMapper extends MyMapper<TbNtRegexQua> {

    /**
     * 添加公告资质表达式关系表
     * @return
     */
    Integer insertTbNtRegexQua(TbNtRegexQua tbNtRegexQua);

    /**
     * 根据公告id、标段id删除资质表达式关系表
     * @param tbNtRegexQua
     * @return
     */
    Integer deleteTbNtRegexQuaByNtIdAndNtEditId(TbNtRegexQua tbNtRegexQua);
}