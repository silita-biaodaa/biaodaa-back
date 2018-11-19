package com.silita.dao;

import com.silita.model.TbFinService;
import com.silita.utils.MyMapper;

import java.util.List;

public interface TbFinServiceMapper extends MyMapper<TbFinService> {

    /**
     * 根据查询条件获取金融服务信息列表
     * @param tbFinService
     * @return
     */
    public List<TbFinService> listFinService(TbFinService tbFinService);

    /**
     * 根据查询条件获取全部金融服务信息列表
     * @param tbFinService
     * @return
     */
    public List<TbFinService> listAllFinService(TbFinService tbFinService);

    /**
     * 根据查询条件获取金融服务信息条数
     * @param tbFinService
     * @return
     */
    public Integer countFinService(TbFinService tbFinService);

}