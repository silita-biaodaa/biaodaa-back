package com.silita.service;

import com.silita.model.DicCommon;
import com.silita.model.SysArea;
import com.silita.model.TbNtMian;
import com.silita.model.TbNtTenders;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-27 16:52
 * 招标公告service
 */
public interface INoticeZhaoBiaoService {

    /**
     * 获取公告编辑固定数据
     * @return
     */
    Map<String, String> listFixedEditData();

    /**
     * 获取公告筛选状态
     * @return
     */
    Map<String, Object> listBulletinStatus();

    /**
     * 根据type获取省份评标办法
     * @param dicCommon
     * @return
     */
    List<Map<String, Object>> listDicCommonNameByType(DicCommon dicCommon);

    /**
     * 根据area_parent_id获取地区数据
     * @param sysArea
     * @return
     */
    List<Map<String, Object>> listSysAreaByParentId(SysArea sysArea);

    /**
     * 根据查询条件获取公告列表
     * @return
     */
    Map<String, Object> listNtMain(TbNtMian tbNtMian);

    /**
     * 根据查询条件生成excel
     * @param tbNtMian
     * @return
     */
    HSSFWorkbook listTendersDetail(TbNtMian tbNtMian);

    /**
     * 更新公告状态
     * @param tbNtMian
     */
    void updateNtMainStatus(TbNtMian tbNtMian);


    /**
     * 添加招标公告标段
     * @param tbNtTenders
     */
    String insertNtTenders(TbNtTenders tbNtTenders);

    /**
     * 更新招标公告标段
     * @param tbNtTenders
     */
    void updateNtTenders(TbNtTenders tbNtTenders);

    /**
     * 根据公告id获取标段信息
     * @param tbNtTenders
     * @return
     */
    List<TbNtTenders> listNtTenders(TbNtTenders tbNtTenders);
}
