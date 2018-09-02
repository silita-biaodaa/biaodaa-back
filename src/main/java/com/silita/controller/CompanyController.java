package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.TbCompany;
import com.silita.model.TbCompanyInfoHm;
import com.silita.model.TbCompanyQualification;
import com.silita.model.TbCompanySecurityCert;
import com.silita.service.ICompanyInfoHmService;
import com.silita.service.ICompanyQualificationService;
import com.silita.service.ICompanySecurityCertService;
import com.silita.service.ICompanyService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.Map;

@RequestMapping("/company")
@Controller
public class CompanyController extends BaseController {

    @Autowired
    ICompanyService companyService;
    @Autowired
    ICompanyInfoHmService companyInfoHmService;
    @Autowired
    ICompanySecurityCertService companySecurityCertService;
    @Autowired
    ICompanyQualificationService companyQualificationService;

    /**
     * 企业列表
     *
     * @param company
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> listCompany(@RequestBody TbCompany company, ServletRequest request) {
        return successMap(companyService.getCompanyList(company));
    }

    /**
     * 人工添加企业列表
     *
     * @param companyInfoHm
     * @param request
     * @return
     */
    @RequestMapping(value = "/art/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> artListCompany(@RequestBody TbCompanyInfoHm companyInfoHm, ServletRequest request) {
        return successMap(companyInfoHmService.getCompanyInfoList(companyInfoHm));
    }

    /**
     * 人工保存企业
     *
     * @param companyInfoHm
     * @param request
     * @return
     */
    @RequestMapping(value = "/art/save", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> artSaveCompany(@RequestBody TbCompanyInfoHm companyInfoHm, ServletRequest request) {
        return companyInfoHmService.saveCompanyInfo(companyInfoHm, JWTUtil.getUsername(request));
    }

    /**
     * 删除人工保存的企业
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/art/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> artDelCompany(@RequestBody Map<String, Object> param) {
        companyInfoHmService.delCompanyInfo(MapUtils.getString(param, "pkid"));
        return successMap(null);
    }

    /**
     * 统一信用代码
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/creditCode/detail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> creditCodeDetail(@RequestBody Map<String, Object> param) {
        return successMap(companyService.getCreditCode(param));
    }

    /**
     * 保存统一信用代码
     *
     * @param companyInfoHm
     * @return
     */
    @RequestMapping(value = "/creditCode/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> creditCodeAdd(@RequestBody TbCompanyInfoHm companyInfoHm, ServletRequest request) {
        return companyInfoHmService.saveCreditCode(companyInfoHm,JWTUtil.getUsername(request));
    }

    /**
     * 删除统一信用代码
     *
     * @param companyInfoHm
     * @return
     */
    @RequestMapping(value = "/creditCode/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> creditCodeDel(@RequestBody TbCompanyInfoHm companyInfoHm, ServletRequest request) {
        companyInfoHmService.delCreditCode(companyInfoHm);
        return successMap(null);
    }

    /**
     * 企业名称
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/comName/detail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> comNameDetail(@RequestBody Map<String, Object> param) {
        return successMap(companyService.getComNameList(param));
    }

    /**
     * 保存企业名称
     *
     * @param companyInfoHm
     * @return
     */
    @RequestMapping(value = "/comName/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> comNameAdd(@RequestBody TbCompanyInfoHm companyInfoHm, ServletRequest request) {
        return companyInfoHmService.saveComName(companyInfoHm,JWTUtil.getUsername(request));
    }

    /**
     * 删除企业名称
     *
     * @param pkid
     * @return
     */
    @RequestMapping(value = "/comName/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> comNameDel(@RequestBody String pkid) {
        companyInfoHmService.delCompanyInfo(pkid);
        return successMap(null);
    }

    /**
     * 安许证号
     *
     * @param companySecurityCert
     * @return
     */
    @RequestMapping(value = "/certNo/detail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> certNoDetail(@RequestBody TbCompanySecurityCert companySecurityCert) {
        companySecurityCert.setCertNo("certNo");
        return successMap(companySecurityCertService.getCompanySecurity(companySecurityCert));
    }

    /**
     * 添加安许证号
     *
     * @param companySecurityCert
     * @return
     */
    @RequestMapping(value = "/certNo/Add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> certNoAdd(@RequestBody TbCompanySecurityCert companySecurityCert, ServletRequest request) {
        return companySecurityCertService.addCertNo(companySecurityCert,JWTUtil.getUsername(request));
    }

    /**
     * 添加安许证号
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/security/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> securityNoDel(@RequestBody Map<String,Object> param) {
        companySecurityCertService.delCompanySeu(MapUtils.getString(param,"pkid"));
        return successMap(null);
    }

    /**
     * 安全认证
     *
     * @param companySecurityCert
     * @return
     */
    @RequestMapping(value = "/security/detail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> securityDetail(@RequestBody TbCompanySecurityCert companySecurityCert) {
        return successMap(companySecurityCertService.getCompanySecurity(companySecurityCert));
    }

    /**
     * 添加安全认证
     *
     * @param companySecurityCert
     * @return
     */
    @RequestMapping(value = "/security/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> securityAdd(@RequestBody TbCompanySecurityCert companySecurityCert) {
        return successMap(companySecurityCertService.getCompanySecurity(companySecurityCert));
    }

    /**
     * 企业资质
     *
     * @param companyQualification
     * @return
     */
    @RequestMapping(value = "/qual/detail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> qualDetail(@RequestBody TbCompanyQualification companyQualification) {
        return successMap(companyQualificationService.getCompanyQualList(companyQualification));
    }

    /**
     * 添加企业资质
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/qual/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> qualAdd(@RequestBody Map<String,Object> param,ServletRequest request) {
        return companyQualificationService.addCompanyQual(param,JWTUtil.getUsername(request));
    }

    /**
     * 添加企业资质
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/qual/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> qualDel(@RequestBody Map<String,Object> param) {
        companyQualificationService.delCompanyQual(MapUtils.getString(param,"pkid"));
        return successMap(null);
    }
}
