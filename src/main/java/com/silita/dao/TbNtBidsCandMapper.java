package com.silita.dao;

import com.silita.model.TbNtBidsCand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbNtBidsCandMapper {

    /**
     * 批量添加中标候选人
     * @param tbNtBidsCands
     */
    void batchInsertNtBidsCand(List<TbNtBidsCand> tbNtBidsCands);

    /**
     * 批量更新中标候选人
     * @param tbNtBidsCands
     */
    void batchUpdateNtBidsCand(@Param("tbNtBidsCands") List<TbNtBidsCand> tbNtBidsCands);
}