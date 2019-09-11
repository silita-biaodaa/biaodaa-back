package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.TbRole;
import com.silita.model.TbUser;
import com.silita.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Autowired
    private IRoleService roleService;


    /**
     * 添加角色及权限
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addRole", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> addRole(@RequestBody Map<String,Object> param) {
        return roleService.addRole(param);
    }
    /**
     * 修改角色及权限
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> updateRole(@RequestBody Map<String,Object> param) {
        return roleService.updateRole(param);
    }
    /**
     * 获取所有角色
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/roleAll", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> roleAll() {
        return this.successMap(roleService.getRoleAll());
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> list(@RequestBody TbRole role) {
        return this.successMap(roleService.getRoleList(role));
    }




}
