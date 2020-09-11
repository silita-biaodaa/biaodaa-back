package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.service.IHighwayService;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 公路 人工解析里程数
 *
 * @author Antoneo
 * @create 2020-09-07 11:46
 **/
@Controller
@RequestMapping("/highway")
public class HighwayController extends BaseController {

    @Resource
    IHighwayService highwayService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> list(@RequestBody Map<String, Object> param) {
        int pageNo=MapUtils.getInteger(param,"pageNo");
        int pageSize=MapUtils.getInteger(param,"pageSize");
        int sourceType=MapUtils.getIntValue(param,"sourceType");
        String nameKey=MapUtils.getString(param,"nameKey");
        String province=MapUtils.getString(param,"province");
        int isOpt=MapUtils.getIntValue(param,"isOpt");
        return highwayService.list(pageNo,pageSize,sourceType,nameKey,province,isOpt);
    }


    @ResponseBody
    @RequestMapping(value = "/show", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> show(@RequestBody Map<String, Object> param){
        String pkid=MapUtils.getString(param,"pkid");
        String type=MapUtils.getString(param,"type");
        return highwayService.show(pkid,type);
    }

    @ResponseBody
    @RequestMapping(value = "/reset", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> reset(@RequestBody Map<String, Object> param){
        String pkid=MapUtils.getString(param,"pkid");
        String type=MapUtils.getString(param,"type");
        return highwayService.reset(pkid,type);
    }

    @ResponseBody
    @RequestMapping(value = "/release", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> release(@RequestBody Map<String, Object> param){
        String pkid=MapUtils.getString(param,"pkid");
        String type=MapUtils.getString(param,"type");
        return highwayService.release(pkid,type);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> update(@RequestBody Map<String, Object> param){
        String pkid=MapUtils.getString(param,"pkid");
        String type=MapUtils.getString(param,"type");
        String mileageMan=MapUtils.getString(param,"mileageMan");
        return highwayService.update(pkid,type,mileageMan);
    }

    @ResponseBody
    @RequestMapping(value = "/total", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Map<String,Object> total(){
        return highwayService.count();
    }

    @ResponseBody
    @RequestMapping(value = "/provinces", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Map<String,Object> provinces(){
        return highwayService.provinces();
    }


}
