package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.service.IAliasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
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

    /**
     *别名搜素
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listMap", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> listMap(@RequestBody Map<String,Object> param){
        List<Map<String, Object>> list = aliasService.gitAliasListStdCode(param);
        return successMap(list);
    }

    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> del(@RequestBody Map<String,Object> param){
        return aliasService.delAilas(param);
    }

}
