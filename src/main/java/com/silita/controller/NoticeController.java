package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.TbNtMian;
import com.silita.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/notice")
@Controller
public class NoticeController extends BaseController {

    @Autowired
    INoticeService  noticeService;

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
}
