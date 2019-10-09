package com.silita.controller;

import com.silita.commons.shiro.token.JWTToken;
import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.model.TbUser;
import com.silita.service.IUserService;
import com.silita.service.ModuleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-10 16:49
 */
@Controller
@RequestMapping("/authorize")
public class AuthorizeController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ModuleService moduleService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> login(@RequestBody TbUser user) {
        Map result = new HashMap();
        Integer integer = userService.existssqlPhone(user);
        if(null == integer || integer == 0){
            result.put("code", 0);
            result.put("msg", "手机号不存在");
            return result;
        }
        TbUser login = userService.login(user);
        if(login == null){
            result.put("code", 0);
            result.put("msg", "手机号或密码错误");
            return result;
        }

        //// TODO: 2019/8/30 需要改动，根据手机号和password登录
        Subject subject = SecurityUtils.getSubject();
        String tokenStr = JWTUtil.sign(login.getUserName(), login.getPassword(),login.getUid(),login.getPhone());
        JWTToken jwtToken = new JWTToken(tokenStr);
        try {
            subject.login(jwtToken);
            result.put("code", 1);
            result.put("data", tokenStr);
            result.put("msg", "登录成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code",0);
            result.put("msg",e.getMessage());
        }
        return result;
    }

//    @RequiresAuthentication
    @RequestMapping("main")
    @ResponseBody
    public String main() {
        return "main";
    }

    @RequiresRoles("admin")
    @RequestMapping("role")
    @ResponseBody
    public String testRole() {
        System.out.println("##############");
        return "admin";
    }

    @RequiresRoles("tearch")
    @RequestMapping("role2")
    @ResponseBody
    public String testRole2() {
        System.out.println("##############");
        return "admin";
    }

}
