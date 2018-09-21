package com.silita.dao;

import com.silita.model.TbNtMian;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface TbNtMianMapper {

    /**
     * 根据查询条件获取公告列表
     * @param tbNtMian
     */
    public List<Map<String, Object>> listNtMain(TbNtMian tbNtMian);

    /**
     * 根据查询条件获取公告列表及标段
     * @param tbNtMian
     * @return
     */
    public List<LinkedHashMap<String, Object>> listTendersDetail(TbNtMian tbNtMian);

    /**
     * 根据查询条件获取公告条数
     * @param tbNtMian
     * @return
     */
    public Integer countNtMian(TbNtMian tbNtMian);

    /**
     * 根据主键删除公告id
     * @param tbNtMian
     */
    public void deleteNtMainByPkId(TbNtMian tbNtMian);

    /**
     * 根据主键更新公告状态（0：新建；1：已编辑；2：已审核）、类型（公告类目（大类）：1：招标；2：中标）
     * @param tbNtMian
     */
    public void updateCategoryAndStatusByPkId(TbNtMian tbNtMian);

    /**
     * 根据主键更新 公告类型（0：新建；1：已编辑；2：已审核）、类型（公告类目（大类）：1：招标；2：中标） 公告类型（小类）
     * 项目金额 项目工期 评标办法
     * @param tbNtMian
     */
    public void updateNtMainByPkId(TbNtMian tbNtMian);

    /**
     * 根据pkid获取 公告类目（大类）：1：招标；2：中标
     * @param tbNtMian
     * @return
     */
    public String getNtCategoryByPkId(TbNtMian tbNtMian);

    /**
     * 更新公告标段个数
     * @param tbNtMian
     */
    public void updateSegCountByPkid(TbNtMian tbNtMian);

    /**
     * 根据pkid获取 标段个数
     * @param tbNtMian
     * @return
     */
    public Integer getSegCountByPkId(TbNtMian tbNtMian);

}