package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.service.IFeedbackService;
import com.silita.service.mongodb.MongodbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/feedback")
public class FeedbackController extends BaseController {

    @Autowired
    IFeedbackService feedbackService;
    @Autowired
    MongodbService mongodbService;

    /**
     * 等级列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> feedbackList(@RequestBody Map<String, Object> param) {
        return this.successMap(feedbackService.listFeedback(param));
    }

    /**
     * 反馈列表
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/new/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> newList(@RequestBody Map<String, Object> param) {
        mongodbService.isNull(param);
        return feedbackService.getlistFeedback(param);
    }

    /**
     * 反馈统计
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/count", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> count() {
        return this.successMap(feedbackService.getFeedbackCount());
    }
    /**
     * 修改备注
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateRemark", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> updateRemark(@RequestBody Map<String, Object> param, ServletRequest request) {
        param.put("optBy",JWTUtil.getUid(request));
        feedbackService.updateRemark(param);
        return this.successMap();
    }
    /**
     * 修改反馈状态
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateState", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> updateState(@RequestBody Map<String, Object> param,ServletRequest request) {
        param.put("optBy",JWTUtil.getUid(request));
        feedbackService.updateState(param);
        return this.successMap();
    }



}
