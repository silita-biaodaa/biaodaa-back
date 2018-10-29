package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.service.ICompanyHighwayGradeService;
import com.silita.service.ICompanySecurityCertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 企业信誉维护
 */
@Controller
@RequestMapping("corp/requ")
public class CorporateReputationController extends BaseController {

    @Autowired
    ICompanyHighwayGradeService companyHighwayGradeService;
    @Autowired
    ICompanySecurityCertService companySecurityCertService;

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map list(@RequestBody Map param) {
        if ("highway_grade".equals(param.get("tabType"))) {
            return successMap(companyHighwayGradeService.getCompanyHighwayGradeForCompanyList(param));
        } else if ("win_record".equals(param.get("tabType"))) {
            return successMap(null);
        } else if ("safety_permission_cert".equals(param.get("tabType")) || "safety_cert".equals(param.get("tabType"))) {
            return successMap(companySecurityCertService.listCompanySecurity(param));
        } else if ("undesirable".equals(param.get("tabType"))) {
            return successMap(null);
        }
        return successMap(null);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map del(@RequestBody Map param) {
        if ("highway_grade".equals(param.get("tabType"))) {
            companyHighwayGradeService.deleteCompanyHigwagGrade(param);
        } else if ("win_record".equals(param.get("tabType"))) {
        } else if ("safety_permission_cert".equals(param.get("tabType")) || "safety_cert".equals(param.get("tabType"))) {
            companySecurityCertService.delCompanySecurity(param);
        } else if ("undesirable".equals(param.get("tabType"))) {
        } else if ("safety_cert".equals(param.get("tabType"))) {
        }
        return successMap();
    }
}
