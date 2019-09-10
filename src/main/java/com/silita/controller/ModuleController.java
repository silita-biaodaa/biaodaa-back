package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/module")
public class ModuleController extends BaseController {
    @Autowired
    private ModuleService moduleService;
    /**
     * 展示可编辑的权限
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateUserModule")
    public Map<String, Object> updateUserModule(@RequestBody Map<String,Object> param) {
        List<Map<String, Object>> module = moduleService.getUpdateUserModule(param);
        return this.successMap(module);
    }
    /**
     * 展示可添加的权限
     * @return
     */
    @ResponseBody
    @RequestMapping("/addUserModule")
    public Map<String, Object> addUserModule() {
        List<Map<String, Object>> module = moduleService.queryAddUserModule();
        return this.successMap(module);
    }


    /**
     * 菜单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public Map<String, Object> list(ServletRequest request) {
        Map<String,Object> param = new HashMap<>();
        param.put("uid",JWTUtil.getUid(request));
        Map<String, Object> module = moduleService.getModule(param);
        return this.successMap(module);
    }




}
