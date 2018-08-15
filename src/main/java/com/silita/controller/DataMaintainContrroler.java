package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.model.DicAlias;
import com.silita.model.DicCommon;
import com.silita.service.IDataMaintainService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-10 16:49
 */
@Controller
@RequestMapping("/dataMaintain")
@RequiresAuthentication
public class DataMaintainContrroler {

    @Autowired
    IDataMaintainService dataMaintainService;

    @RequestMapping(value = "/listProvince", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listProvince() {
        Map result = new HashMap();
        result.put("code", 1);
        try{
            Map data = dataMaintainService.listProvince();
            result.put("data", data);
            result.put("msg", "获取省份code成功！");
        } catch (Exception e) {
            result.put("code",0);
            result.put("msg",e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/insertPbMode", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> insertPbMode(@RequestBody DicCommon dicCommon, ServletRequest request) {
        Map result = new HashMap();
        result.put("code", 1);
        try{
            String userName = JWTUtil.getUsername(request);
            dicCommon.setCreateBy(userName);
            String msg = dataMaintainService.insertPbModeBySource(dicCommon);
            result.put("msg", msg);
            if(StringUtils.isEmpty(msg)) {
                result.put("code",0);
                result.put("msg","评标办法已存在，添加失败！");
            }
        } catch (Exception e) {
            result.put("code",0);
            result.put("msg",e.getMessage());
        }
       return result;
    }

    @RequestMapping(value = "/updatePbMode",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> updatePbMode(@RequestBody DicCommon dicCommon, ServletRequest request) {
        Map result = new HashMap();
        result.put("code", 1);
        try{
            String userName = JWTUtil.getUsername(request);
            dicCommon.setUpdateBy(userName);
            String msg = dataMaintainService.updatePbModeById(dicCommon);
            result.put("msg", msg);
            if(StringUtils.isEmpty(msg)) {
                result.put("code",0);
                result.put("msg","评标办法已存在，更新失败！");
            }
        } catch (Exception e) {
            result.put("code",0);
            result.put("msg",e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/listPbMode",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listPbMode(@RequestBody DicCommon dicCommon) {
        Map result = new HashMap();
        result.put("code", 1);
        result.put("data", null);
        try{
            List<DicCommon> list = dataMaintainService.listPbModeBySource(dicCommon);
            result.put("msg", "获取评标办法成功！");
            result.put("data", list);
        } catch (Exception e) {
            result.put("code",0);
            result.put("msg",e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deletePbMode",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> deletePbMode(@RequestBody Map<String,Object> param) {
        Map result = new HashMap();
        result.put("code", 1);
        try{
            String idsStr = (String) param.get("idsStr");
            dataMaintainService.deletePbModeByIds(idsStr);
            result.put("msg", "删除评标办法成功！");
        } catch (Exception e) {
            result.put("code",0);
            result.put("msg",e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/insertPbModeAlias", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> insertPbModeAlias(@RequestBody DicAlias dicAlias, ServletRequest request) {
        Map result = new HashMap();
        result.put("code", 1);
        try{
            String userName = JWTUtil.getUsername(request);
            dicAlias.setCreateBy(userName);
            String msg = dataMaintainService.insertPbModeAliasByStdCode(dicAlias);
            result.put("msg", msg);
            if(StringUtils.isEmpty(msg)) {
                result.put("code",0);
                result.put("msg","评标办法别名已存在，添加失败！");
            }
        } catch (Exception e) {
            result.put("code",0);
            result.put("msg",e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/updatePbModeAlias",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> updatePbModeAlias(@RequestBody DicAlias dicAlias, ServletRequest request) {
        Map result = new HashMap();
        result.put("code", 1);
        try{
            String userName = JWTUtil.getUsername(request);
            dicAlias.setUpdateBy(userName);
            String msg = dataMaintainService.updatePbModeAliasById(dicAlias);
            result.put("msg", msg);
            if(StringUtils.isEmpty(msg)) {
                result.put("code",0);
                result.put("msg","评标办法别名已存在，更新失败！");
            }
        } catch (Exception e) {
            result.put("code",0);
            result.put("msg",e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/listPbModeAlias",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listPbModeAlias(@RequestBody DicAlias dicAlias) {
        Map result = new HashMap();
        result.put("code", 1);
        result.put("data", null);
        try{
            List<DicAlias> list = dataMaintainService.listPbModeAliasByStdCode(dicAlias);
            result.put("msg", "获取评标办法别名成功！");
            result.put("data", list);
        } catch (Exception e) {
            result.put("code",0);
            result.put("msg",e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deletePbModeAlias",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> deletePbModeAlias(@RequestBody Map<String,Object> param) {
        Map result = new HashMap();
        result.put("code", 1);
        try{
            String idsStr = (String) param.get("idsStr");
            dataMaintainService.deletePbModeAliasByIds(idsStr);
            result.put("msg", "删除评标办法别名成功！");
        } catch (Exception e) {
            result.put("code",0);
            result.put("msg",e.getMessage());
        }
        return result;
    }
}
