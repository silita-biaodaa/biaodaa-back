package com.silita.dao;

import com.silita.model.TbNtBids;
import com.silita.model.TbNtMian;
import org.apache.ibatis.annotations.Param;

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
     * 根据公告pkid获取TbNtBids
     * @param tbNtBids
     * @return
     */
    List<TbNtBids> listNtBidsByNtId(TbNtBids tbNtBids);

    /**
     * 根据中标标段pkid删除NtBids
     * @return
     */
    Integer batchDeleteNtBidsByPkId(@Param("tableName") String tableName, @Param("array") Object[] array);

}