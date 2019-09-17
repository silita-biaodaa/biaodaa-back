package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.model.TbNtMian;
import com.silita.model.TbNtText;
import com.silita.service.ICommonService;
import com.silita.service.INoticeZhaoBiaoService;
import com.silita.service.INtContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@RequestMapping("/common")
@Controller
public class CommonController extends BaseController {

    @Autowired
    ICommonService commonService;
    @Autowired
    INtContentService ntContentService;
    @Autowired
    INoticeZhaoBiaoService noticeZhaoBiaoService;
    private TbNtMian tbNtMian;

    /**
     * 返回地区（省/市）
     *
     * @return
     */
    @RequestMapping(value = "/area", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> area() {
        return successMap(commonService.getArea());
    }

    /**
     * 获取公告原文详情
     *
     * @param
     * @return
     */
/*    @RequestMapping(value = "/detail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> recycelDetail(@RequestBody Map<String,Object> param) throws IOException {

        return successMap(ntContentService.queryCentent(param));
    }*/
    @RequestMapping(value = "/listRelevantNotice", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> listRelevantNotice(@RequestBody TbNtMian tbNtMian) {
        return super.successMap(noticeZhaoBiaoService.listNtMain(tbNtMian));
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> list(@RequestBody Map<String, Object> param) {
        return super.successMap(commonService.getList(param));
    }

    /**
     * 批量删除公告词典数据
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/deleteDicCommonIds", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> deleteDicCommonIds(@RequestBody Map<String, Object> param) {
        commonService.deleteDicCommonIds(param);
        return super.successMap();
    }

    /**
     * 修改评标名称
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateDicCommonId", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> updateDicCommonId(@RequestBody Map<String, Object> param) {
        return commonService.updateDicCommonId(param);
    }



}
