package com.silita.dao;

import com.silita.model.TbNtChange;

public interface TbNtChangeMapper {

    /**
     * 添加公告变更信息
     * @param tbNtChange
     */
    void insertTbNtChange(TbNtChange tbNtChange);

    /**
     * 更新公告变更信息
     * @param tbNtChange
     */
    void updateTbNtChangeByPkId(TbNtChange tbNtChange);
}