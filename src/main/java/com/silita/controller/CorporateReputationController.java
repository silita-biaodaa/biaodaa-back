package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.service.ICompanyAwardsService;
import com.silita.service.ICompanyBadService;
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
    @Autowired
    ICompanyAwardsService companyAwardsService;
    @Autowired
    ICompanyBadService companyBadService;

    /**
     * 删除
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map del(@RequestBody Map param) {
        if ("highway_grade".equals(param.get("tabType"))) {
            companyHighwayGradeService.deleteCompanyHigwagGrade(param);
        } else if ("win_record".equals(param.get("tabType"))) {
            companyAwardsService.batchDelCompanyAwards(param);
        } else if ("safety_permission_cert".equals(param.get("tabType")) || "safety_cert".equals(param.get("tabType"))) {
            companySecurityCertService.checkAllDelCompanySecurity(param);
        } else if ("undesirable".equals(param.get("tabType"))) {
            companyBadService.checkAllDelCompanyBad(param);
        }
        return successMap();
    }

    /**
     * 删除
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/check/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map checkDel(@RequestBody Map param) {
        if ("highway_grade".equals(param.get("tabType"))) {
            companyHighwayGradeService.checkAllDeleteCompanyHigwagGrade(param);
        } else if ("win_record".equals(param.get("tabType"))) {
            companyAwardsService.checkAllDelCompanyAwards(param);
        } else if ("safety_permission_cert".equals(param.get("tabType")) || "safety_cert".equals(param.get("tabType"))) {
            companySecurityCertService.checkAllDelCompanySecurity(param);
        } else if ("undesirable".equals(param.get("tabType"))) {
            companyBadService.checkAllDelCompanyBad(param);
        }
        return successMap();
    }

    /**
     * 列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map list(@RequestBody Map param) {
        if ("highway_grade".equals(param.get("tabType"))) {
            return successMap(companyHighwayGradeService.getCompanyHighwayGradeForCompanyList(param));
        } else if ("win_record".equals(param.get("tabType"))) {
            return successMap(companyAwardsService.getCompanyAwardsList(param));
        } else if ("safety_permission_cert".equals(param.get("tabType")) || "safety_cert".equals(param.get("tabType"))) {
            return successMap(companySecurityCertService.listCompanySecurity(param));
        } else if ("undesirable".equals(param.get("tabType"))) {
            return successMap(companyBadService.getCompanyBadList(param));
        }
        return successMap(null);
    }
}
