package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.service.ITbVipProfitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/vip/profits")
@Controller
public class VipProfitsController extends BaseController {

    @Autowired
    private ITbVipProfitsService iTbVipProfitsService;

    /**
     * 会员明细
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> profits(@RequestBody Map<String, Object> param) {
        return successMap(iTbVipProfitsService.getVipProfitsSingle(param));
    }
}
