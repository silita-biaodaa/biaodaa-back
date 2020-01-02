package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.SysUserInfo;
import com.silita.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserInfoController extends BaseController {

    @Autowired
    IUserInfoService userInfoService;

    /**
     * 用户统计
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/count", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> userCount() {
        return successMap(userInfoService.getUserCount());
    }

    /**
     * 用户锁定
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/lock", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> userLock(@RequestBody Map<String, Object> param, ServletRequest request) {
        String uid = JWTUtil.getUid(request);
        param.put("optBy",uid);
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

    /**
     * 用户信息列表
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/info/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> infoList(@RequestBody Map<String,Object> param) {
        return userInfoService.getUserInfo(param);
    }

    /**
     * 获取活跃用户统计
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/active/count", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> count(){
        return successMap(userInfoService.getActiveUserCount());
    }

   @ResponseBody
   @RequestMapping(value = "/active/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> activeList(@RequestBody Map<String,Object> param){
        return userInfoService.getActiveUserList(param);
    }

    /**
     *个人用户信息
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/info/personage", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> personage(@RequestBody Map<String,Object> param){
        return successMap(userInfoService.getSingleUserInfo(param));
    }



    /**
     * 修改备注
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateRemark", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> updateRemark(@RequestBody Map<String,Object> param,ServletRequest request){
        param.put("optBy",JWTUtil.getUid(request));
        userInfoService.updateRemark(param);
        return successMap();
    }


    /**
     * 邀请人信息
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/inviter", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> inviter(@RequestBody Map<String,Object> param){
        return successMap(userInfoService.getInviterInfo(param));
    }

    /**
     * 订单统计
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/order/count", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> orderCount(){
        return successMap(userInfoService.getOrderCount());
    }




}
