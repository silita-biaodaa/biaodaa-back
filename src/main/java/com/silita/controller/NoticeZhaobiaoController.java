package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.*;
import com.silita.service.INoticeZhaoBiaoService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
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

    //

    @RequestMapping(value = "/listNtMain",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listNtMain(@RequestBody TbNtMian tbNtMian) {
        return super.successMap(noticeZhaoBiaoService.listNtMain(tbNtMian));
    }

    @RequestMapping(value = "/exportTendersExcel",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public void listTendersDetail(@RequestBody TbNtMian tbNtMian, HttpServletResponse response) throws IOException {
        HSSFWorkbook work = noticeZhaoBiaoService.listTendersDetail(tbNtMian);
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename="+ System.currentTimeMillis());
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        OutputStream out=response.getOutputStream();
        work.write(out);
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/updateNtMainStatus",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateNtMainStatus(@RequestBody TbNtMian tbNtMian) {
        noticeZhaoBiaoService.updateNtMainStatus(tbNtMian);
        return successMap(null);
    }

    //#####################招标标段信息######################

    @RequestMapping(value = "/insertNtTenders", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> insertNtTenders(@RequestBody TbNtTenders tbNtTenders, ServletRequest request) {
        Map result = new HashMap<String,Object>();
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

    @RequestMapping(value = "/listNtTenders",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listNtTenders(@RequestBody TbNtTenders tbNtTenders) {
        return super.successMap(noticeZhaoBiaoService.listNtTenders(tbNtTenders));
    }

    @RequestMapping(value = "/deleteNtTendersByPkId",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> deleteNtTendersByPkId(@RequestBody Map<String,Object> param) {
        noticeZhaoBiaoService.deleteNtTendersByPkId(param);
        return super.successMap(null);
    }

    //#####################变更标段########################

    @RequestMapping(value = "/insertTbNtChange", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> insertTbNtChange(@RequestBody TbNtChange tbNtChange, ServletRequest request) {
        String userName = JWTUtil.getUsername(request);
        tbNtChange.setCreateBy(userName);
        noticeZhaoBiaoService.insertTbNtChange(tbNtChange);
        return successMap(null);
    }

    @RequestMapping(value = "/updateTbNtChange", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateTbNtChange(@RequestBody TbNtChange tbNtChange, ServletRequest request) {
        String userName = JWTUtil.getUsername(request);
        tbNtChange.setUpdateBy(userName);
        noticeZhaoBiaoService.updateTbNtChangeByPkId(tbNtChange);
        return successMap(null);
    }

    //#####################招标文件######################

    @RequestMapping(value = "/insertZhaoBiaoFilePath", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> insertZhaoBiaoFilePath(@RequestBody SysFiles sysFiles, ServletRequest request) {
        String userName = JWTUtil.getUsername(request);
        sysFiles.setCreateBy(userName);
        noticeZhaoBiaoService.insertZhaoBiaoFiles(sysFiles);
        return successMap(null);
    }

    @RequestMapping(value = "/listZhaoBiaoFiles",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listSysFilesByPkid(@RequestBody SysFiles sysFiles) {
        return super.successMap(noticeZhaoBiaoService.listZhaoBiaoFilesByPkid(sysFiles));
    }

    @RequestMapping(value = "/deleteZhaoBiaoFile",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> deleteSysFilesByPkid(@RequestBody Map<String,Object> param) {
        String idsStr = (String) param.get("idsStr");
        noticeZhaoBiaoService.deleteZhaoBiaoFilesByPkid(idsStr);
        return super.successMap(null);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> del(@RequestBody TbNtMian tbNtMian,ServletRequest request) {
        noticeZhaoBiaoService.delNtMainInfo(tbNtMian,JWTUtil.getUsername(request));
        return super.successMap(null);
    }

    @RequestMapping(value = "/insertNtAssociateGp",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> insertNtAssociateGp(@RequestBody Map<String,Object> param, ServletRequest request) {
        param.put("createBy", JWTUtil.getUsername(request));
        noticeZhaoBiaoService.insertNtAssociateGp(param);
        return super.successMap(null);
    }

    @RequestMapping(value = "/deleteNtAssociateGp",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> deleteNtAssociateGp(@RequestBody List<TbNtAssociateGp> tbNtAssociateGps) {
        noticeZhaoBiaoService.deleteNtAssociateGp(tbNtAssociateGps);
        return super.successMap(null);
    }

    @RequestMapping(value = "/listNtAssociateGp",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listNtAssociateGp(@RequestBody TbNtAssociateGp tbNtAssociateGp) {
        return super.successMap(noticeZhaoBiaoService.listNtAssociateGp(tbNtAssociateGp));
    }

}
