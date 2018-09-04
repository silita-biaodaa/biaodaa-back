package com.silita.dao;

import com.silita.model.SysFiles;

import java.util.List;

public interface SysFilesMapper {

    /**
     * 根据BizId获取系统文件列表
     * @param sysFiles
     * @return
     */
    List<SysFiles> listSysFilesByBizId(SysFiles sysFiles);

    /**
     * 添加系统文件列表
     * @param sysFiles
     */
    void insertSysFiles(SysFiles sysFiles);

    /**
     * 根据pkid更新系统文件
     * @param sysFiles
     */
    void updateSysFilesByPkId(SysFiles sysFiles);

    /**
     * 根据Pkid删除系统文件记录
     * @param ids
     */
    void deleteSysFilesByPkid(Object[] ids);
}