package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.service.IAliasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/alias")
@Controller
public class AliasController extends BaseController {

    @Autowired
    IAliasService aliasService;

    /**
     *别名搜素
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> list(@RequestBody Map<String,Object> param){
        return successMap(aliasService.getAliasList(param));
    }

}
