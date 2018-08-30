package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.model.TbCompany;
import com.silita.service.ICompanyService;
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

    /**
     * 企业列表
     * @param company
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> listCompany(@RequestBody TbCompany company, ServletRequest request){
        return successMap(companyService.getCompanyList(company));
    }
}
