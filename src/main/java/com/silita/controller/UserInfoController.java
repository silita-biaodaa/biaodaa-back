package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.model.SysUserInfo;
import com.silita.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserInfoController extends BaseController {

    @Autowired
    IUserInfoService userInfoService;

    /**
     * 用户统计
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/count", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> userCount(@RequestBody Map<String, Object> param) {
        return successMap(userInfoService.userCount(param));
    }

    /**
     * 用户锁定
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/lock", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> userLock(@RequestBody Map<String, Object> param) {
        userInfoService.userLock(param);
        return successMap();
    }

    /**
     * 用户列表
     *
     * @param userInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> list(@RequestBody SysUserInfo userInfo) {
        return successMap(userInfoService.listUserInfo(userInfo));
    }
}
