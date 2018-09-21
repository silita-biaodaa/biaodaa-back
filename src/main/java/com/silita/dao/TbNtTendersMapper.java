package com.silita.dao;

import com.silita.model.TbNtTenders;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据公告id\标段编号更新招标公告标段
     * @param tbNtTenders
     */
    void updateNtTendersByNtIdAndSegment(TbNtTenders tbNtTenders);

    /**
     * 根据公告id获取标段信息
     * @param tbNtTenders
     * @return
     */
    List<TbNtTenders> listNtTendersByNtId(TbNtTenders tbNtTenders);

    /**
     * 根据标段pkid获取标段
     * @param tbNtTenders
     * @return
     */
    TbNtTenders getNtTendersByNtIdByPkId(TbNtTenders tbNtTenders);

    /**
     * 根据pkid删除招标公告标段
     * @param tableName
     * @param array
     */
    void deleteNtTendersByPkId(@Param("tableName") String tableName, @Param("array") Object[] array);

    /**
     * 添加变更信息时，同时标段表对应字段一起改变
     * @param params
     */
    void updateChangeFieldValue(Map params);

    /**
     * 根据公告pkid\编辑明细编码更新项目类型(中标编辑时使用)
     * @param tbNtTenders
     * @return
     */
    Integer updateProTypeAndPbModeByNtIdAndEditCode(TbNtTenders tbNtTenders);

    /**
     * 根据公告pkid获取招标标段个数
     * @param tbNtTenders
     * @return
     */
    Integer countNtTendersByNtId(TbNtTenders tbNtTenders);

    /**
     * 根据标段pkid获取公告pkid
     * @param tbNtTenders
     * @return
     */
    String getNtIdByNtId(TbNtTenders tbNtTenders);
}