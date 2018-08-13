package com.silita.dao;

import com.silita.model.DicCommon;
import com.silita.utils.MyMapper;

import java.util.List;

public interface DicCommonMapper extends MyMapper<DicCommon> {

    /**
     * 添加公共数据词典表数据
     * @param dicCommon
     */
    public void insertDicCommon(DicCommon dicCommon);

    /**
     * 根据id更新公共数据词典表数据
     * @param dicCommon
     */
    public void updateDicCommonById(DicCommon dicCommon);

    /**
     * 根据type获取公共数据词典表数据（）
     * @param dicCommon
     * @return
     */
    public List<DicCommon> listDicCommonByType(DicCommon dicCommon);

    /**
     * 根据type获取公共数据词典表数据条数
     * @param dicCommon
     * @return
     */
    public Integer getDicCommonCountByType(DicCommon dicCommon);

    /**
     * 根据id删除公共数据词典表数据
     * @param ids
     */
    public void deleteDicCommonByIds(Object[] ids);
}