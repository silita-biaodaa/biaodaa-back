package com.silita.dao;

import com.silita.model.TbNtRegexGroup;
import com.silita.utils.MyMapper;

public interface TbNtRegexGroupMapper extends MyMapper<TbNtRegexGroup> {

    /**
     * 查询公告标段资质组之间的关系
     * @param tbNtRegexGroup
     * @return
     */
    TbNtRegexGroup queryNtRegexGroup(TbNtRegexGroup tbNtRegexGroup);
}