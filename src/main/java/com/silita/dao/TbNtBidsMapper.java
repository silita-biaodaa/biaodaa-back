package com.silita.dao;

import com.silita.model.TbNtBids;

import java.util.List;

public interface TbNtBidsMapper {

    /**
     * 添加中标标段编辑明细
     * @param tbNtBids
     * @return
     */
    Integer insertTbNtBids(TbNtBids tbNtBids);

    /**
     * 根据公告pkid、标段id更新中标标段信息
     * @param tbNtBids
     * @return
     */
    Integer updateTbNtBidsByNtIdAndSegment(TbNtBids tbNtBids);

    /**
     * 根据公告pkid、标段id判断标段信息是否存在
     * @param tbNtBids
     * @return
     */
    Integer countNtBidsByNtIdAndSegment(TbNtBids tbNtBids);

    /**
     * 根据公告pkid获取中标标段信息
     * @param tbNtBids
     * @return
     */
    List<TbNtBids> listNtBidsByNtId(TbNtBids tbNtBids);

}