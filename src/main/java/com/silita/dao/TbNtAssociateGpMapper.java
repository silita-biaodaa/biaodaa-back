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
     * 根据公告id获取关联关系列表
     * @param tbNtAssociateGp
     * @return
     */
    List<Map<String, Object>> listNtAssociateGpByNtId(TbNtAssociateGp tbNtAssociateGp);

    /**
     * 根据公告id获取关联关系个数
     * @param tbNtAssociateGp
     * @return
     */
    Integer countNtAssociateGpByNtId(TbNtAssociateGp tbNtAssociateGp);

    /**
     *
     * @return
     */
    List<TbNtAssociateGp> getRelGpByNtIds(@Param("array")Object[] array, @Param("tableName")String tableName);

    /**
     * 根据公告id更新公告状态
     * @param tbNtAssociateGp
     */
    void updateRelTypeByBtId(TbNtAssociateGp tbNtAssociateGp);

    /**
     * 根据中标公告pkid获取招标公告pkid
     * @param tbNtAssociateGp
     * @return
     */
    List<String> getNtIdByNtId(TbNtAssociateGp tbNtAssociateGp);

}