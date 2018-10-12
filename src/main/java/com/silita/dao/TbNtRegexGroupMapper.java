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

    /**
     * 添加资质组关系
     * @param tbNtRegexGroup
     */
    Integer insertNtRegexGroup(TbNtRegexGroup tbNtRegexGroup);

    /**
     * 根据公告id、标段id更新资质组关系
     * @param tbNtRegexGroup
     * @return
     */
    Integer updateNtRegexGroupByNtIdAndNtEditId(TbNtRegexGroup tbNtRegexGroup);

    /**
     * 根据公告id、标段id删除资质组关系
     * @param tbNtRegexGroup
     * @return
     */
    Integer deleteNtRegexGroupByNtIdAndNtEditId(TbNtRegexGroup tbNtRegexGroup);
}