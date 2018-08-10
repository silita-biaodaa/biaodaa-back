package com.silita.controller;

import com.silita.model.DicCommon;
import com.silita.service.IDataMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class DataMaintainContrroler {

    @Autowired
    IDataMaintainService dataMaintainService;

    @RequestMapping(value = "/insertPbMode", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> insertPbMode(@RequestBody DicCommon dicCommon) {
        Map result = new HashMap();
        result.put("code", 1);
        result.put("data", null);
        try{
            dataMaintainService.insertPbModeBySource(dicCommon);
            result.put("msg", "添加评标办法成功！");
        } catch (Exception e) {
            result.put("code",0);
            result.put("msg",e.getMessage());
        }
       return result;
    }

    @RequestMapping(value = "/updatePbMode",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> updatePbMode(@RequestBody DicCommon dicCommon) {
        Map result = new HashMap();
        result.put("code", 1);
        result.put("data", null);
        try{
            dataMaintainService.updatePbModeById(dicCommon);
            result.put("msg", "更新评标办法成功！");
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
}
