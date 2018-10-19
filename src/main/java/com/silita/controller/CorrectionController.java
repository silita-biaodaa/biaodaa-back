package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.model.Snatchurl;
import com.silita.service.ICorrectionService;
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
 * Date: 2018-10-18 15:02
 */
@Controller
@RequestMapping("/correction")
public class CorrectionController extends BaseController {

    @Autowired
    ICorrectionService correctionService;

    @RequestMapping(value = "/listNotice",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listSnatchurl(@RequestBody Snatchurl snatchurl) {
        return super.successMap(correctionService.listSnatchurl(snatchurl));
    }

    @RequestMapping(value = "/deleteNotice", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateNtText(@RequestBody Snatchurl snatchurl) {
        correctionService.updateSnatchurlIsShowById(snatchurl);
        return successMap(null);
    }
}
