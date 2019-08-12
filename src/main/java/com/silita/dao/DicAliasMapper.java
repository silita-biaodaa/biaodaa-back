package com.silita.dao;

import com.silita.model.DicAlias;
import com.silita.model.TbCompanyAwards;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface DicAliasMapper extends MyMapper<DicAlias> {

    /**
     * 添加词典别名
     *
     * @param dicAlias
     */
    public void insertDicAlias(DicAlias dicAlias);

    /**
     * 更新词典别名
     *
     * @param dicAlias
     */
    public void updateDicAliasById(DicAlias dicAlias);

    /**
     * 根据stdCode获取词典别名列表
     *
     * @param dicAlias
     * @return
     */
    public List<DicAlias> listDicAliasByStdCode(DicAlias dicAlias);

    /***
     * 根据stdCode获取词典别名列表
     * @param
     * @return
     */
    List<Map<String,Object>> queryAliasListCode(DicAlias dicAlias);

    /**
     * 别名统计
     * @param dicAlias
     * @return
     */
    Integer queryAliasListCodeCount(DicAlias dicAlias);

    /**
     * 根据stdCode获取词典别名个数
     *
     * @param dicAlias
     * @return
     */
    public Integer getDicAliasCountByStdCode(DicAlias dicAlias);

    /**
     * 批量删除词典别名
     *
     * @param ids
     */
    public void deleteDicAliasByIds(Object[] ids);

    void delAilasByIds(Map<String,Object> param);

    /**
     * 根据名称查询
     *
     * @param param
     * @return
     */
    Integer queryAliasByName(Map<String, Object> param);

    /**
     * 根据std_code批量删除词典别名
     */
    public void deleteDicAliasByStdCodes(Object[] ids);

    void insertAilas(List<DicAlias> list);

}