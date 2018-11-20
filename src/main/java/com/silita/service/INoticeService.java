package com.silita.service;

import com.silita.model.*;

import java.util.List;
import java.util.Map;

public interface INoticeService {

    /**
     * 添加公告
     * @param mian
     */
    Map<String,Object> addNotice(TbNtMian mian);

    /**
     * 更新公告内容
     * @param tbNtText
     */
    void updateNtText(TbNtText tbNtText);

    /**
     * 更新公告状态
     * @param tbNtMian
     */
    void updateNtMainStatus(TbNtMian tbNtMian);

    /**
     * 逻辑删除公告
     * @param main
     */
    void delNtMainInfo(TbNtMian main, String username);

    /**
     * 根据查询条件获取公告列表
     * @return
     */
    Map<String, Object> listNtMain(TbNtMian tbNtMian);



    /**
     * 获取公告筛选状态
     * @return
     */
    Map<String, Object> listBulletinStatus();

    /**
     * 获取招标公告编辑框固定数据
     * @return
     */
    Map<String, Object> listFixedEditData();

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
     * 添加或者更新标段变更信息
     * @param tbNtChange
     */
    void saveTbNtChange(TbNtChange tbNtChange);

}
