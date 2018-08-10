package com.silita.controller;

import com.silita.commons.shiro.token.JWTToken;
import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.model.TbUser;
import com.silita.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/13.
 */
@Controller
@RequestMapping("/")
public class AuthorizeController {

    @Autowired
    private IUserService userService;

    @RequestMapping("login")
    @ResponseBody
    public Map<String, Object> login(TbUser user) {
        Map result = new HashMap();

        Subject subject = SecurityUtils.getSubject();
        String tokenStr = JWTUtil.sign(user.getUserName(), user.getPassword());
        JWTToken jwtToken = new JWTToken(tokenStr);
        try {
            subject.login(jwtToken);
            result.put("code", 1);
            result.put("data", tokenStr);
        } catch (Exception e) {
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
