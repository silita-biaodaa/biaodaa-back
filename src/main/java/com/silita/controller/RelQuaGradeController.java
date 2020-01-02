package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.service.IRelQuaGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/rel")
public class RelQuaGradeController extends BaseController {
    @Autowired
    private IRelQuaGradeService relQuaGradeService;

    /**
     * 修改资质名称维护-资质等级
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping("/upd")
    public Map<String,Object> upd(@RequestBody Map<String,Object> param){
        return relQuaGradeService.updateRelQuaGrade(param);
    }

}
