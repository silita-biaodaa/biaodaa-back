package com.silita.dao;

import com.silita.model.TbNtBidsCand;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
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

    /**
     * 批量删除中标候选人
     * @param array
     */
    void batchDeleteNtBidsCandByNtBidsId(@Param("array") Object[] array);

    /**
     * 根据NtBidsId、Number获取中标候选人信息
     * @param tbNtBidsCand
     * @return
     */
    LinkedHashMap<String, Object> getNtBidsCandByNtBidsIdAndNumber(TbNtBidsCand tbNtBidsCand);

}