package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.service.IAliasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

@RequestMapping("/alias")
@Controller
public class AliasController extends BaseController {

    @Autowired
    IAliasService aliasService;

    /**
     * 别名搜素
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> list(@RequestBody Map<String, Object> param) {
        return successMap(aliasService.gitAliasListStdCode(param));
    }


    /**
     * 删除别名
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> del(@RequestBody Map<String, Object> param) {
        return aliasService.delAilasByIds(param);
    }
    /**
     * 删除资质别名
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/qual/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> delQual(@RequestBody Map<String, Object> param) {
        return aliasService.delAilasById(param);
    }
    /**
     * 添加等级别名
     *
     * @param param
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> add(@RequestBody Map<String, Object> param, ServletRequest request) {
        //String userName = JWTUtil.getUsername(request);
        param.put("createBy", "system");
        return aliasService.insertLevelAilas(param);
    }

}
