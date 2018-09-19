package com.silita.service;

import com.silita.model.SysFiles;
import com.silita.model.TbNtBids;
import com.silita.model.TbNtMian;
import com.silita.model.TbNtTenders;

import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-09-17 16:29
 * 中标编辑service
 */
public interface INoticeZhongBiaoService {

    /**
     * 根据中标公告pkid 获取对应的招标公告编辑明细
     * @param tbNtMian
     * @return
     */
    List<TbNtTenders> listNtTenders(TbNtMian tbNtMian);

    /**
     * 根据招标编辑明细pkid 批量删除标段信息
     * @param params
     */
    public void deleteNtTendersByPkId(Map params);


    /**
     * 根据中标pkid 获取关联公告列表信息
     * @param tbNtMian
     * @return
     */
    Map<String, Object> listNtAssociateGp(TbNtMian tbNtMian);

    /**
     * 根据中标公告pkid 获取招标文件
     * @param tbNtMian
     * @return
     */
    List<SysFiles> listZhaoBiaoFilesByPkId(TbNtMian tbNtMian);

    /**
     * 添加或修改标段信息
     * @param tbNtBids
     * @return
     */
    String saveNtBids(TbNtBids tbNtBids);

    /**
     * 根据公告pkid获取中标标段信息（包含中标候选人）
     * @return
     */
    List<TbNtBids> listTbNtBidsByNtId(TbNtBids tbNtBids);

}