package com.silita.service.impl;

import com.silita.dao.SysLogsMapper;
import com.silita.model.SysLogs;
import com.silita.service.ILogsService;
import com.silita.service.abs.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class LogsServiceImpl extends AbstractService implements ILogsService {
    @Autowired
    private SysLogsMapper logsMapper;

    /**
     * 操作列表
     * @param logs
     * @return
     */
    @Override
    public Map<String, Object> getLogsList(SysLogs logs) {
        Map<String,Object> resultMap = new HashMap<>();
        try{
            resultMap.put("total", logsMapper.queryLogsListCount(logs));
            resultMap.put("list", logsMapper.queryLogsList(logs));
        }catch (Exception e){
            e.printStackTrace();
        }

        return super.handlePageCount(resultMap, logs);
    }
}
