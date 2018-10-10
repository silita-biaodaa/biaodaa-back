package com.silita.dao;

import com.silita.model.TbNtQuaGroup;
import com.silita.utils.MyMapper;

import java.util.List;

public interface TbNtQuaGroupMapper extends MyMapper<TbNtQuaGroup> {

    /**
     * 查询分组内资质关系
     * @param ntQuaGroup
     * @return
     */
    List<TbNtQuaGroup> queryNtQuaGroupList(TbNtQuaGroup ntQuaGroup);
}