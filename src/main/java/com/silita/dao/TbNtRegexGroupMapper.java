package com.silita.dao;

import com.silita.model.TbNtRegexGroup;
import com.silita.utils.MyMapper;

public interface TbNtRegexGroupMapper extends MyMapper<TbNtRegexGroup> {

    /**
     * 根据公告id、标段id获取资质组关系
     * @param tbNtRegexGroup
     * @return
     */
    TbNtRegexGroup getNtRegexGroupByNtIdAndNtEditId(TbNtRegexGroup tbNtRegexGroup);
}