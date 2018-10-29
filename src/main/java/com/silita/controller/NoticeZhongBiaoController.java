package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.TbNtBids;
import com.silita.model.TbNtChange;
import com.silita.model.TbNtMian;
import com.silita.model.TbNtRegexGroup;
import com.silita.service.INoticeZhongBiaoService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-09-17 16:59
 * 中标模块
 */
@Controller
@RequestMapping("/zhongbiao")
public class NoticeZhongBiaoController extends BaseController {

    @Autowired
    private INoticeZhongBiaoService noticeZhongBiaoService;


    @RequestMapping(value = "/listNtTenders",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listNtTenders(@RequestBody TbNtMian tbNtMian) {
        return super.successMap(noticeZhongBiaoService.listNtTenders(tbNtMian));
    }

    @RequestMapping(value = "/deleteNtTenders",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> deleteNtTenders(@RequestBody Map<String,Object> param) {
        noticeZhongBiaoService.deleteNtTendersByPkId(param);
        return super.successMap(null);
    }

    @RequestMapping(value = "/listNtAssociateGp",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listNtAssociateGp(@RequestBody TbNtMian tbNtMian) {
        return super.successMap(noticeZhongBiaoService.listNtAssociateGp(tbNtMian));
    }

    @RequestMapping(value = "/listZhaoBiaoFiles",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listZhaoBiaoFiles(@RequestBody TbNtMian tbNtMian) {
        return super.successMap(noticeZhongBiaoService.listZhaoBiaoFilesByPkId(tbNtMian));
    }

    @RequestMapping(value = "/saveTbNtBids",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> saveTbNtBids(@RequestBody TbNtBids tbNtBids, ServletRequest request) {
        Map result = new HashMap<String,Object>();
        result.put("code", 1);
        try{
            String userName = JWTUtil.getUsername(request);
            tbNtBids.setCreateBy(userName);
            String msg = noticeZhongBiaoService.saveNtBids(tbNtBids);
            result.put("msg", msg);
        } catch (Exception e) {
            result.put("code",0);
            result.put("msg",e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/listTbNtBids",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listTbNtBids(@RequestBody TbNtBids tbNtBids) {
        return super.successMap(noticeZhongBiaoService.listTbNtBidsByNtId(tbNtBids));
    }

    @RequestMapping(value = "/deleteTbNtBids",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> deleteTbNtBids(@RequestBody Map params) {
        noticeZhongBiaoService.deleteTbNtBidsByPkId(params);
        return super.successMap(null);
    }

    @RequestMapping(value = "/exportBidsDetail",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public void exportBidsDetail(@RequestBody TbNtMian tbNtMian, HttpServletResponse response) {
        try{
            HSSFWorkbook work = noticeZhongBiaoService.listBids(tbNtMian);
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ System.currentTimeMillis());
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            OutputStream out=response.getOutputStream();
            work.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/insertTbNtChange", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> insertTbNtChange(@RequestBody TbNtChange tbNtChange, ServletRequest request) {
        String userName = JWTUtil.getUsername(request);
        tbNtChange.setCreateBy(userName);
        noticeZhongBiaoService.saveTbNtChange(tbNtChange);
        return successMap(null);
    }

    @RequestMapping(value = "/delNtMain", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> delNtMain(@RequestBody TbNtMian tbNtMian,ServletRequest request) {
        tbNtMian.setCreateBy(JWTUtil.getUsername(request));
        noticeZhongBiaoService.delNtMain(tbNtMian);
        return super.successMap(null);
    }

    @RequestMapping(value = "/listCompany", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> listCompany(@RequestBody Map params) {
        return super.successMap(noticeZhongBiaoService.listCompany(String.valueOf(params.get("queryKey"))));
    }

    @RequestMapping(value = "/getQualRelationStr",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> getQualRelationStr(@RequestBody TbNtRegexGroup tbNtRegexGroup) {
        return super.successMap(noticeZhongBiaoService.getQualRelationStr(tbNtRegexGroup));
    }
}
