package com.silita.service;

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
     * 获取招标公告编辑明细
     * @param tbNtMian
     * @return
     */
    List<TbNtTenders> listNtTenders(TbNtMian tbNtMian);

    /**
     * 获取关联公告列表
     * @param tbNtMian
     * @return
     */
    Map<String, Object> listNtAssociateGp(TbNtMian tbNtMian);
}
