package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.TbCompany;
import com.silita.model.TbCompanyInfoHm;
import com.silita.service.ICompanyInfoHmService;
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
     * @param param
     * @return
     */
    @RequestMapping(value = "/creditCode/save", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> creditCodeSave(@RequestBody Map<String, Object> param) {
        return successMap(companyService.getCreditCode(param));
    }

    /**
     * 企业名称
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/comName/detail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> comNameCodeDetail(@RequestBody Map<String, Object> param) {
        return successMap(companyService.getComNameList(param));
    }
}
