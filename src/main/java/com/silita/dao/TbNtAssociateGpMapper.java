package com.silita.dao;

import com.silita.model.TbNtAssociateGp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TbNtAssociateGpMapper {

    /**
     * 添加公告关联关系
     * @param lists
     */
    void batchInsertNtAssociateGp(@Param("lists")List<TbNtAssociateGp> lists, @Param("tableName")String tableName);

    /**
     * 根据ntId and rel_gp删除关联关系
     * @param tbNtAssociateGp
     */
    void deleteNtAssociateGpByNtIdAndRelGp(TbNtAssociateGp tbNtAssociateGp);

    /**
     * 根据条件获取关联关系列表
     * @param tbNtAssociateGp
     * @return
     */
    List<Map<String, Object>> listNtAssociateGp(TbNtAssociateGp tbNtAssociateGp);

    /**
     * 根据条件获取关联关系个数
     * @param tbNtAssociateGp
     * @return
     */
    Integer countNtAssociateGp(TbNtAssociateGp tbNtAssociateGp);

}