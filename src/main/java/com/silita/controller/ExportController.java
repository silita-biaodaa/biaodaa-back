package com.silita.controller;

import com.silita.common.Constant;
import com.silita.controller.base.BaseController;
import com.silita.service.ICompanyAwardsService;
import com.silita.service.ICompanyBadService;
import com.silita.service.ICompanyHighwayGradeService;
import com.silita.service.ICompanySecurityCertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/export")
public class ExportController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ExportController.class);

    @Autowired
    ICompanyAwardsService companyAwardsService;
    @Autowired
    ICompanyHighwayGradeService companyHighwayGradeService;
    @Autowired
    ICompanyBadService companyBadService;
    @Autowired
    ICompanySecurityCertService companySecurityCertService;

    /**
     * 公司维护信息导出
     *
     * @param param
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> companyCorpExport(@RequestBody Map<String, Object> param) {
        String fileUrl = null;
        try {
            if ("highway_grade".equals(param.get("tabType"))) {
                fileUrl = companyHighwayGradeService.batchExportCompanyHighwayGrade(param);
            } else if ("win_record".equals(param.get("tabType"))) {
                fileUrl = companyAwardsService.batchExprotAwards(param);
            } else if ("safety_permission_cert".equals(param.get("tabType")) || "safety_cert".equals(param.get("tabType"))) {
                fileUrl = companySecurityCertService.batchExportCompanySecu(param);
            } else if ("undesirable".equals(param.get("tabType"))) {
                fileUrl = companyBadService.batchExportCompanyBad(param);
            }
            return successMap(fileUrl);
        } catch (Exception e) {
            logger.error("export excel error ！", e);
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("code", Constant.CODE_ERROR_500);
            resultMap.put("msg", Constant.MSG_ERROR_500);
            return resultMap;
        }
    }
}
