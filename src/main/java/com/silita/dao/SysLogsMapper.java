package com.silita.dao;

import com.silita.model.SysLogs;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * 操作
 */
public interface SysLogsMapper extends MyMapper<SysLogs> {
    /**
     * 添加操作日志
     * @param param
     */
    void insertLogs(Map<String,Object> param);

    /**
     * 查询及筛选操作列表
     * @param logs
     * @return
     */
    List<Map<String,Object>> queryLogsList(SysLogs logs);

    /**
     * 查询及筛选操作列表统计
     * @param logs
     * @return
     */
    Integer queryLogsListCount(SysLogs logs);

}