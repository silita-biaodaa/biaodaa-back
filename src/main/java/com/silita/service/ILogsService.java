package com.silita.service;

import com.silita.model.SysLogs;

import java.util.Map;

public interface ILogsService {
    /**
     * 操作列表
     * @param logs
     * @return
     */
    Map<String,Object> getLogsList(SysLogs logs);
}
