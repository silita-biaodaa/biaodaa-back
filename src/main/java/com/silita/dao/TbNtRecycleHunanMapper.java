package com.silita.dao;

import com.silita.model.TbNtRecycle;
import com.silita.utils.MyMapper;

import java.util.List;

public interface TbNtRecycleHunanMapper extends MyMapper<TbNtRecycle> {

    /**
     * 查询回收站公告列表
     * @param recycle
     * @return
     */
    List<TbNtRecycle> queryNtRecycleList(TbNtRecycle recycle);

    /**
     * 查询公告列表总数
     * @param recycle
     * @return
     */
    Integer queryNtRecycleTotal(TbNtRecycle recycle);

    /**
     * 查询回收站公告详情
     * @param recycle
     * @return
     */
    TbNtRecycle queryNtRecycleDetail(TbNtRecycle recycle);

    /**
     * 删除回收站公告（逻辑删除）
     * @param recycle
     * @return
     */
    Integer deleteRecyleLogic(TbNtRecycle recycle);

    /**
     * 添加回收站公告
     * @param recycle
     * @return
     */
    Integer inertRecycleForNtMain(TbNtRecycle recycle);
}