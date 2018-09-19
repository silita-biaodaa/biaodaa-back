package com.silita.dao;

import com.silita.model.SysFiles;
import org.apache.ibatis.annotations.Param;

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
     * 根据Pkid、省份来源删除系统文件记录
     * @param array
     * @param source
     */
    void deleteSysFilesByPkid(@Param("array") Object[] array, @Param("source") String source);

    /**
     * 根据公告pkid、source获取招标文件个数
     * @param sysFiles
     * @return
     */
    Integer countSysFilesByBizIdAndSource(SysFiles sysFiles);
}