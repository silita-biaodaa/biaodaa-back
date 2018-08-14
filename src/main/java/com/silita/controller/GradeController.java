package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.DicAlias;
import com.silita.model.DicCommon;
import com.silita.service.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * 等级
 */
@Controller
@RequestMapping("/grade")
public class GradeController extends BaseController {

    @Autowired
    IGradeService gradeService;

    /**
     * 等级列表
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> list(@RequestBody Map<String, Object> param) {
        return this.successMap(gradeService.getGradeList(param));
    }


    /**
     * 保存等级
     *
     * @param dicCommon
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> save(@RequestBody DicCommon dicCommon, ServletRequest request) {
        gradeService.saveGrade(dicCommon, JWTUtil.getUsername(request));
        return this.successMap(null);
    }

    /**
     * 删除等级
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> del(@RequestBody Map<String, Object> param) {
        gradeService.delGrade(param);
        return this.successMap(null);
    }

    /**
     * 添加等级别名
     *
     * @param alias
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/alias/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> aliasAdd(@RequestBody DicAlias alias, ServletRequest request) {
        alias.setCreateBy(JWTUtil.getUsername(request));
        gradeService.addGradeAlias(alias);
        return this.successMap(null);
    }

    /**
     * 添加等级别名
     *
     * @param alias
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list/qual", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> listQual(@RequestBody DicAlias alias, ServletRequest request) {
        alias.setCreateBy(JWTUtil.getUsername(request));
        gradeService.addGradeAlias(alias);
        return this.successMap(null);
    }
}
