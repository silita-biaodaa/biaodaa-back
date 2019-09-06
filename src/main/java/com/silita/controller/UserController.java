package com.silita.controller;

import com.silita.common.Constant;
import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.TbUser;
import com.silita.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/backstage/user")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    /**
     * 用户锁定或解锁
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateLock", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> updateLock(@RequestBody Map<String, Object> param,ServletRequest request) {
        param.put("optBy",JWTUtil.getUid(request));
        userService.updateLock(param);
        return this.successMap();
    }
    /**
     * 修改密码
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> updatePassword(@RequestBody Map<String,Object> param,ServletRequest request){
        param.put("optBy",JWTUtil.getUid(request));
        return userService.updatePassword(param);
    }

    /**
     * 重置密码
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateResetPassword", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> updateResetPassword(@RequestBody Map<String,Object> param,ServletRequest request) {
        param.put("optBy",JWTUtil.getUid(request));
        userService.updateResetPassword(param);
        return this.successMap();
    }
    /**
     * 账号管理查询及筛选
     *
     * @param tbUser
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/accountList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> accountList(@RequestBody TbUser tbUser) {
        return this.successMap(userService.getAccountList(tbUser));
    }



    /**
     * 编辑管理员信息及权限
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateAdministrator", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> updateAdministrator(@RequestBody Map<String,Object> param) {
        return userService.updateAdministrator(param);
    }
    /**
     * 添加管理员及分配角色
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> add(@RequestBody Map<String,Object> param, ServletRequest request) {
        param.put("optBy",JWTUtil.getUid(request));
        return userService.addAdministrator(param);
    }




}
