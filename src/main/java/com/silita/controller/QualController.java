package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.DicAlias;
import com.silita.service.IQualService;
import com.silita.service.IRelQuaGradeService;
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
     * 查询资质列表查询及筛选
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> list(@RequestBody Map param) {
        return qualService.getDicQuaListMaps(param);
    }



    /**
     * 添加资质
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/adds", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> adds(@RequestBody Map<String,Object> param, ServletRequest request) {
        param.put("createBy", "system");
        qualService.addQualTest(param);
        return successMap();
    }

    /**
     * 添加资质
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> add(@RequestBody Map<String,Object> param, ServletRequest request) {
        param.put("createBy", "system");
        return qualService.addQual(param);
    }


    /**
     * 删除资质
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> del(@RequestBody Map<String,Object> param) {
        qualService.delQual(param);
        return successMap();
    }
    /**
     * 修改资质
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/upd", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> upd(@RequestBody Map<String,Object> param,ServletRequest request) {
        String userName = JWTUtil.getUsername(request);
        param.put("updateBy", userName);
        return qualService.updQuals(param);
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
        alias.setCreateBy("system");
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


    /**
     * 全部资质等级列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/qualGrade/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> qualGradeList(){
        return successMap(qualService.qualGradeList());
    }

    /**
     * 全部资质等级列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/qualGrade/notic/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> qualGradeNoticList(){
        return successMap(qualService.listQual());
    }
    /**
     * 资质下拉选项
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/qualSpinner", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> qualSpinner(){
        return successMap(qualService.getQua());
    }

    /**
     * 查询资质属性
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBizType", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> getBizType(@RequestBody Map<String,Object> param) {
        return successMap(qualService.getBizType(param));
    }
    /**
     * 修改资质属性
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateBizType", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> updateBizType(@RequestBody Map<String,Object> param) {
        qualService.updateBizType(param);
        return successMap();
    }






}
