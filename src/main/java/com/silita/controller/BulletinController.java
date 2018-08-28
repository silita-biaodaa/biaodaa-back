package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.model.DicCommon;
import com.silita.service.IBulletinService;
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
 * Date: 2018-08-27 16:49
 * 公告编辑
 */

@Controller
@RequestMapping("/bulletin")
public class BulletinController extends BaseController {

    @Autowired
    IBulletinService bulletinService;


    @RequestMapping(value = "/listFixedEditData", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listProvince() {
        return super.successMap(bulletinService.listFixedEditData());
    }


    @RequestMapping(value = "/listPbMode",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listPbMode(@RequestBody DicCommon dicCommon) {
        return super.successMap(bulletinService.listDicCommonNameByType(dicCommon));
    }
}
