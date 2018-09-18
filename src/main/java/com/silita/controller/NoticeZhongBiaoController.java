package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.model.TbNtMian;
import com.silita.service.INoticeZhongBiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/listNtAssociateGp",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listNtAssociateGp(@RequestBody TbNtMian tbNtMian) {
        return super.successMap(noticeZhongBiaoService.listNtAssociateGp(tbNtMian));
    }


}
