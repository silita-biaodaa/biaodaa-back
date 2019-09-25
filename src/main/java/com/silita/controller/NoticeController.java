package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.*;
import com.silita.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/notice")
@Controller
public class NoticeController extends BaseController {

    @Autowired
    INoticeService noticeService;

    /**
     * 添加公告
     * @param tbNtMian
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public Map<String, Object> addNotice(@RequestBody TbNtMian tbNtMian, HttpServletRequest request) {
        tbNtMian.setCreateBy(JWTUtil.getUsername(request));
        return noticeService.addNotice(tbNtMian);
    }

    @RequestMapping(value = "/updateNtText", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateNtText(@RequestBody TbNtText tbNtText) {
        noticeService.updateNtText(tbNtText);
        return successMap(null);
    }

    @RequestMapping(value = "/updateNtMainStatus",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateNtMainStatus(@RequestBody TbNtMian tbNtMian) {
        noticeService.updateNtMainStatus(tbNtMian);
        return successMap(null);
    }

    @RequestMapping(value = "/deleteNtMain", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> del(@RequestBody TbNtMian tbNtMian,ServletRequest request) {
        noticeService.delNtMainInfo(tbNtMian,JWTUtil.getUsername(request));
        return super.successMap(null);
    }

    @RequestMapping(value = "/listNtMain",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listNtMain(@RequestBody TbNtMian tbNtMian) {
        return super.successMap(noticeService.listNtMain(tbNtMian));
    }



    @RequestMapping(value = "/listNoticeStatus", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listNoticeStatus() {
        return super.successMap(noticeService.listBulletinStatus());
    }

    @RequestMapping(value = "/listFixedEditData", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listProvince() {
        return super.successMap(noticeService.listFixedEditData());
    }

    @RequestMapping(value = "/listPbMode",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listPbMode(@RequestBody DicCommon dicCommon) {
        return super.successMap(noticeService.listDicCommonNameByType(dicCommon));
    }

    @RequestMapping(value = "/listSysArea",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listSysArea(@RequestBody SysArea sysArea) {
        return super.successMap(noticeService.listSysAreaByParentId(sysArea));
    }


    @RequestMapping(value = "/insertTbNtChange", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> insertTbNtChange(@RequestBody TbNtChange tbNtChange, ServletRequest request) {
        String userName = JWTUtil.getUsername(request);
        tbNtChange.setCreateBy(userName);
        noticeService.saveTbNtChange(tbNtChange);
        return successMap(null);
    }

    /**
     * 获取公告统计
     * @return
     */
    @RequestMapping(value = "/noticeCount", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> noticeCount() {
        return successMap(noticeService.getNoticeCount());
    }
    /**
     * 获取站点公告统计
     * @return
     */
    @RequestMapping(value = "/siteNoticeCount", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> siteNoticeCount(@RequestBody Map<String,Object> param) {
        return successMap(noticeService.getCount(param));
    }

}
