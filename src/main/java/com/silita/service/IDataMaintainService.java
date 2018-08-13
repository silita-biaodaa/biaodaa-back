package com.silita.service;

import com.silita.model.DicAlias;
import com.silita.model.DicCommon;

import java.util.List;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-10 15:47
 */
public interface IDataMaintainService  {

    /**
     * 根据省份添加评标办法
     */
    void insertPbModeBySource(DicCommon dicCommon);

    /**
     * 根据省份查询评标办法
     */
    List<DicCommon> listPbModeBySource(DicCommon dicCommon);

    /**
     * 根据主键更新评标办法
     */
    void updatePbModeById(DicCommon dicCommon);

    /**
     * 批量删除评标办法
     * @param idStr
     */
    void deletePbModeByIds(String idStr);

    //----------------------------------

    /**
     * 添加评标办法别名
     * @param dicAlias
     */
    void insertPbModeAliasByStdCode(DicAlias dicAlias);

    /**
     * 根据stdCode查询评标办法别名
     * @param dicAlias
     * @return
     */
    List<DicAlias> listPbModeAliasByStdCode(DicAlias dicAlias);

    /**
     * 根据主键更新评标办法别名
     * @param dicAlias
     */
    void updatePbModeAliasById(DicAlias dicAlias);

    /**
     *  批量删除评标办法别名
     * @param idStr
     */
    void deletePbModeAliasByIds(String idStr);
}
