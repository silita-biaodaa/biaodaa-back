package com.silita.dao;

import com.silita.model.TbNtTenders;

public interface TbNtTendersMapper {

    /**
     * 根据NtId和标段判断公告标段是否存在
     * @param tbNtTenders
     * @return
     */
    Integer countNtTendersByNtIdAndSegment(TbNtTenders tbNtTenders);

    /**
     * 添加招标公告标段
     */
    void insertNtTenders(TbNtTenders tbNtTenders);

    /**
     * 更新招标公告标段
     * @param tbNtTenders
     */
    void updateNtTendersByPkId(TbNtTenders tbNtTenders);
}