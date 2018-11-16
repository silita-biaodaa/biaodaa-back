package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/feedback")
public class FeedbackController extends BaseController {

    @Autowired
    IFeedbackService feedbackService;

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

}
