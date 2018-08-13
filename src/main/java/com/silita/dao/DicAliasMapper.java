package com.silita.dao;

import com.silita.model.DicAlias;
import com.silita.model.DicCommon;
import com.silita.utils.MyMapper;

import java.util.List;

public interface DicAliasMapper extends MyMapper<DicAlias> {

    /**
     * 添加词典别名
     * @param dicAlias
     */
    public void insertDicAlias(DicAlias dicAlias);

    /**
     * 更新词典别名
     * @param dicAlias
     */
    public void updateDicAliasById(DicAlias dicAlias);

    /**
     * 根据stdCode获取词典别名列表
     * @param dicAlias
     * @return
     */
    public List<DicAlias> listDicAliasByStdCode(DicAlias dicAlias);

    /**
     * 根据stdCode获取词典别名个数
     * @param dicCommon
     * @return
     */
    public Integer getDicAliasCountByStdCode(DicCommon dicCommon);

    /**
     * 批量删除词典别名
     * @param ids
     */
    public void deleteDicAliasByIds(Object[] ids);
}