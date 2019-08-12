package com.silita.service;

import com.silita.model.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
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
    Map<String, Object> listFixedEditData();

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

    //
    /**
     * 根据查询条件获取公告列表
     * @return
     */
    Map<String, Object> listNtMain(TbNtMian tbNtMian);

    /**
     * 根据查询条件生成excel
     * @param param
     * @return
     */
    SXSSFWorkbook listTendersDetail(Map<String,Object> param) throws IOException;



    /**
     * 更新公告状态
     * @param tbNtMian
     */
    void updateNtMainStatus(TbNtMian tbNtMian);

    //#####################招标标段信息######################
    /**
     * 添加或者更新招标公告标段
     * @param tbNtTenders
     */
    String saveNtTenders(TbNtTenders tbNtTenders);

    /**
     * 根据公告id获取标段信息
     * @param tbNtTenders
     * @return
     */
    Object listNtTenders(TbNtTenders tbNtTenders);

    /**
     * 根据公告id、标段id获取标段信息
     * @param tbNtTenders
     * @return
     */
    TbNtTenders getNtTendersByNtIdByPkId(TbNtTenders tbNtTenders);

    /**
     * 根据pkid删除标段信息
     * @param params
     */
    void deleteNtTendersByPkId(Map params);

    //#####################变更标段########################
    /**
     * 添加或者更新标段变更信息
     * @param tbNtChange
     */
    void saveTbNtChange(TbNtChange tbNtChange);


    //#####################招标文件######################
    /**
     * 根据公告pkid获取文件列表
     * @param sysFiles
     * @return
     */
    List<SysFiles> listZhaoBiaoFilesByPkid(SysFiles sysFiles);

    /**
     * 添加系统文件列表
     * @param sysFiles
     */
    void insertZhaoBiaoFiles(SysFiles sysFiles);

    /**
     * 根据公告pkid删除文件列表
     * * @param idStr
     */
    void deleteZhaoBiaoFilesByPkid(Map<String,Object> param);

    /**
     * 逻辑删除公告
     * @param main
     * @param username
     */
    void delNtMainInfo(TbNtMian main,String username);

    /**
     * 更新公告内容
     * @param tbNtText
     */
    void updateNtText(TbNtText tbNtText);

    //#################公告关联####################
    /**
     * 添加公告关联关系
     * @param params
     */
    String insertNtAssociateGp(Map params);

    /**
     * 删除公告关联关系
     * @param tbNtAssociateGps
     */
    void deleteNtAssociateGp(List<Map<String, Object>> tbNtAssociateGps);

    /**
     * 获取公告关联关系数据
     * @param tbNtAssociateGp
     * @return
     */
    Map<String, Object> listNtAssociateGp(TbNtAssociateGp tbNtAssociateGp);

    /**
     * 添加资质关系表达式(程序算法生成）
     * @param tbNtRegexQua
     */
    void insertNtRegexQua(TbNtRegexQua tbNtRegexQua);

    /**
     * 添加资质组关系
     * @param tbNtRegexGroups
     */
    void saveTbNtRegexGroup(List<TbNtRegexGroup> tbNtRegexGroups, Map params);

    /**
     * 获取资质小组关系
     * @param tbNtRegexGroup
     * @return
     */
    List<TbNtRegexGroup> listTbQuaGroup(TbNtRegexGroup tbNtRegexGroup);
}
