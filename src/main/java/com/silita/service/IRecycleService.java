package com.silita.service;

import com.silita.model.TbNtRecycle;

import java.util.Map;

/**
 * tb_nt_recycle service
 */
public interface IRecycleService {

    /**
     * 获取回收站公告列表
     * @param recycle
     * @return
     */
    Map<String,Object> getRecycleList(TbNtRecycle recycle);

    /**
     * 批量删除回收站
     * @param param
     */
    void delRecycel(Map<String,Object> param);

    /**
     * 批量恢复
     * @param param
     */
    void recoverRecycle(Map<String,Object> param);
}
