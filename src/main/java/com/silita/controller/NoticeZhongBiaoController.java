package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.TbNtBids;
import com.silita.model.TbNtMian;
import com.silita.service.INoticeZhongBiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        System.out.println(tbNtBids.toString());
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

}
