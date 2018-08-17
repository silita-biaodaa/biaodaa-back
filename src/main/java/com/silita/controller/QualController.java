package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.DicAlias;
import com.silita.model.DicQua;
import com.silita.model.RelQuaGrade;
import com.silita.service.IQualService;
import com.silita.service.IRelQuaGradeService;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * 资质
 * created by zhushuai
 */
@Controller
@RequestMapping("/qual")
@RequiresAuthentication
public class QualController extends BaseController {

    @Autowired
    IQualService qualService;
    @Autowired
    IRelQuaGradeService relQuaGradeService;


    /**
     * 获取资质类别
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/qualCate", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> qualCate() {
        return successMap(qualService.getQualCateList());
    }

    /**
     * 查询
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> list(@RequestBody Map param) {
        return successMap(qualService.getDicQuaList(param));
    }

    /**
     * 添加
     *
     * @param qua
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> add(@RequestBody DicQua qua, ServletRequest request) {
        return qualService.addQual(qua,JWTUtil.getUsername(request));
    }

    /**
     * 删除
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> del(@RequestBody Map<String,Object> param) {
        qualService.delQual(MapUtils.getString(param,"id"));
        return successMap(null);
    }

    /**
     * 添加资质别名
     * @param alias
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/alias/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> aliasAdd(@RequestBody DicAlias alias,ServletRequest request) {
        alias.setCreateBy(JWTUtil.getUsername(request));
        return qualService.aliasAdd(alias);
    }

    /**
     * 修改等级别名
     *
     * @param alias
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/alias/update", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> aliasUpd(@RequestBody DicAlias alias, ServletRequest request) {
        alias.setUpdateBy(JWTUtil.getUsername(request));
        return qualService.updateQuaAlias(alias);
    }

    /**
     * 添加资质等级
     * @param quaGrade
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/grade/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> gradeAdd(@RequestBody RelQuaGrade quaGrade) {
        return relQuaGradeService.addQuaGrade(quaGrade);
    }

    /**
     * 删除资质等级
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/grade/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> gradeDel(@RequestBody Map<String,Object> param) {
        relQuaGradeService.delQuaGrade(param);
        return successMap(null);
    }

    /**
     * 资质等级列表
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/grade/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> gradeList(@RequestBody Map<String,Object> param) {
        return successMap(relQuaGradeService.getQualGradeList(param));
    }
}
