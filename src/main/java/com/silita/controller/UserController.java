package com.silita.controller;

import com.silita.common.Constant;
import com.silita.controller.base.BaseController;
import com.silita.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/backstage/user")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    /**
     * 用户锁定或解锁
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateLock", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> updateLock(@RequestBody Map<String,Object> param){
       userService.updateLock(param);
        return this.successMap();
    }
    /**
     * 修改密码
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateLock", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> updatePassword(@RequestBody Map<String,Object> param){
        Map<String, Object> map = userService.updatePassword(param);
        if(null != map){
            return this.successMap();
        }
        Map<String,Object> maps = new HashMap<>();
        maps.put("code", 0);
        maps.put("msg","手机号码或原密码错误");
        return this.successMap();
    }

}
