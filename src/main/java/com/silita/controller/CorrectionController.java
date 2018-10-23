package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.model.AllZh;
import com.silita.model.Snatchurl;
import com.silita.model.ZhaobiaoDetailOthers;
import com.silita.model.ZhongbiaoDetailOthers;
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

    @RequestMapping(value = "/ListAllZh",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> ListAllZh(@RequestBody AllZh allZh) {
        return super.successMap(correctionService.ListAllZhByName(allZh));
    }

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

    @RequestMapping(value = "/listZhaobiaoDetail",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listZhaobiaoDetail(@RequestBody ZhaobiaoDetailOthers zhaobiaoDetailOthers) {
        return super.successMap(correctionService.listZhaobiaoDetailBySnatchUrlId(zhaobiaoDetailOthers));
    }

    @RequestMapping(value = "/updateZhaobiaoDetail", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateZhaobiaoDetail(@RequestBody ZhaobiaoDetailOthers zhaobiaoDetailOthers) {
        correctionService.updateZhaobiaoDetailById(zhaobiaoDetailOthers);
        return successMap(null);
    }

    @RequestMapping(value = "/listCompany",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listCompany(@RequestBody Map params) {
        return super.successMap(correctionService.listCompanyByNameOrPinYin(String.valueOf(params.get("queryKey"))));
    }

    @RequestMapping(value = "/listZhongbiaoDetail",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listZhongbiaoDetail(@RequestBody ZhongbiaoDetailOthers zhongbiaoDetailOthers) {
        return super.successMap(correctionService.listZhongbiaoDetailBySnatchUrlId(zhongbiaoDetailOthers));
    }

    @RequestMapping(value = "/updateZhongbiaoDetail", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateZhongbiaoDetail(@RequestBody ZhongbiaoDetailOthers zhongbiaoDetailOthers) {
        correctionService.updateZhongbiaoDetailById(zhongbiaoDetailOthers);
        return successMap(null);
    }
}
