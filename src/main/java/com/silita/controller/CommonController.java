package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.model.TbNtMian;
import com.silita.model.TbNtText;
import com.silita.service.ICommonService;
import com.silita.service.INoticeZhaoBiaoService;
import com.silita.service.INtContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@RequestMapping("/common")
@Controller
public class CommonController extends BaseController {

    @Autowired
    ICommonService commonService;
    @Autowired
    INtContentService ntContentService;
    @Autowired
    INoticeZhaoBiaoService noticeZhaoBiaoService;

    /**
     * 返回地区（省/市）
     *
     * @return
     */
    @RequestMapping(value = "/area", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> area() {
        return successMap(commonService.getArea());
    }

    /**
     * 获取公告原文详情
     * @param
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> recycelDetail(@RequestBody Map<String,Object> param) throws IOException {

        return successMap(ntContentService.queryCentent(param));
    }
    @RequestMapping(value = "/listRelevantNotice", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listRelevantNotice(@RequestBody TbNtMian tbNtMian) {
        return super.successMap(noticeZhaoBiaoService.listNtMain(tbNtMian));
    }
}
