package com.silita.service.impl;

import com.silita.dao.SysLogsMapper;
import com.silita.model.SysLogs;
import com.silita.service.ILogsService;
import com.silita.service.abs.AbstractService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
@Service
public class LogsServiceImpl extends AbstractService implements ILogsService {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    private SysLogsMapper logsMapper;

    /**
     * 操作列表
     * @param logs
     * @return
     */
    @Override
    public Map<String, Object> getLogsList(SysLogs logs) {
        String realName = logs.getRealName();
        String optType = logs.getOptType();
        if(StringUtil.isEmpty(realName)){
            logs.setRealName("");
        }
        if(StringUtil.isEmpty(optType)){
            logs.setOptType("");
        }
        Map<String,Object> resultMap = new HashMap<>();
        try{
            resultMap.put("total", logsMapper.queryLogsListCount(logs));
            resultMap.put("list", logsMapper.queryLogsList(logs));
        }catch (Exception e){
            logger.error("操作列表：",e);
        }

        return super.handlePageCount(resultMap, logs);
    }
}
