package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.service.ICommonService;
import com.silita.service.INoticeZhaoBiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/common")
@Controller
public class CommonController extends BaseController {

    @Autowired
    ICommonService commonService;

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

}
