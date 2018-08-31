package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.*;
import com.silita.service.INoticeZhaoBiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-27 16:49
 * 招标公告接口
 */

@Controller
@RequestMapping("/zhaobiao")
public class NoticeZhaobiaoController extends BaseController {

    @Autowired
    INoticeZhaoBiaoService noticeZhaoBiaoService;


    @RequestMapping(value = "/listFixedEditData", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listProvince() {
        return super.successMap(noticeZhaoBiaoService.listFixedEditData());
    }

    @RequestMapping(value = "/listNoticeStatus", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listNoticeStatus() {
        return super.successMap(noticeZhaoBiaoService.listBulletinStatus());
    }

    @RequestMapping(value = "/listPbMode",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listPbMode(@RequestBody DicCommon dicCommon) {
        return super.successMap(noticeZhaoBiaoService.listDicCommonNameByType(dicCommon));
    }

    @RequestMapping(value = "/listSysArea",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listSysArea(@RequestBody SysArea sysArea) {
        return super.successMap(noticeZhaoBiaoService.listSysAreaByParentId(sysArea));
    }

    @RequestMapping(value = "/listNtMain",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listNtMain(@RequestBody TbNtMian tbNtMian) {
        return super.successMap(noticeZhaoBiaoService.listNtMain(tbNtMian));
    }

    @RequestMapping(value = "/updateNtMainStatus",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateNtMainStatus(@RequestBody TbNtMian tbNtMian) {
        noticeZhaoBiaoService.updateNtMainStatus(tbNtMian);
        return successMap(null);
    }

    @RequestMapping(value = "/insertNtTenders", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> insertNtTenders(@RequestBody TbNtTenders tbNtTenders, ServletRequest request) {
        Map result = new HashMap();
        result.put("code", 1);
        try{
            String userName = JWTUtil.getUsername(request);
            tbNtTenders.setCreateBy(userName);
            String msg = noticeZhaoBiaoService.insertNtTenders(tbNtTenders);
            result.put("msg", msg);
            if(StringUtils.isEmpty(msg)) {
                result.put("code",0);
                result.put("msg","标段已存在，添加失败！");
            }
        } catch (Exception e) {
            result.put("code",0);
            result.put("msg",e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/updateNtTenders",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateNtTenders(@RequestBody TbNtTenders tbNtTenders, ServletRequest request) {
        String userName = JWTUtil.getUsername(request);
        tbNtTenders.setUpdateBy(userName);
        noticeZhaoBiaoService.updateNtTenders(tbNtTenders);
        return successMap(null);
    }
}
