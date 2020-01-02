package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.model.SysLogs;
import com.silita.service.ILogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/logs")
public class LogsController extends BaseController {
    @Autowired
    private ILogsService logsService;
    /**
     * 操作列表
     * @param logs
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> list(@RequestBody SysLogs logs){
        return this.successMap(logsService.getLogsList(logs));
    }
}
