package com.silita.dao;

import com.silita.model.TbNtQuaGroup;
import com.silita.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbNtQuaGroupMapper extends MyMapper<TbNtQuaGroup> {

    /**
     * 根据group_id获取资质小组信息
     * @param groupId
     * @return
     */
    List<TbNtQuaGroup> listTbNtQuaGroupByGroupId(@Param("groupId") String groupId);
}