package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.DicQuaAnalysis;
import com.silita.service.IDicQuaAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * 资质解析
 */
@Controller
@RequestMapping("/analysis")
public class QuaAnalysisController extends BaseController {
    @Autowired
    IDicQuaAnalysisService dicQuaAnalysisService;

    /**
     * 获取资质解析列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> qualCate(@RequestBody DicQuaAnalysis dicQuaAnalysis) {
        return successMap(dicQuaAnalysisService.getQuaAnalysisList(dicQuaAnalysis));
    }
    /**
     * 添加资质解析数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> add(@RequestBody Map<String,Object> param, ServletRequest request) {
        String userName = JWTUtil.getUsername(request);
        param.put("createBy", userName);
        return dicQuaAnalysisService.insertQuaAnalysis(param);
    }
    /**
     * 删除资质解析数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> del(@RequestBody Map<String,Object> param) {
        return dicQuaAnalysisService.delQuaAnalysis(param);
    }

}
