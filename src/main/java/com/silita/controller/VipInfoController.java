package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.service.ITbVipInfoService;
import com.silita.service.ITbVipProfitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.Map;

@RequestMapping("/vip")
@Controller
public class VipInfoController extends BaseController {
    @Autowired
    private ITbVipInfoService iTbVipInfoService;
    /**
     * 赠送会员天数
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> add(@RequestBody Map<String, Object> param, ServletRequest request) {
        param.put("optBy",JWTUtil.getUid(request));
        iTbVipInfoService.addVipInfo(param);
        return successMap();
    }

}
