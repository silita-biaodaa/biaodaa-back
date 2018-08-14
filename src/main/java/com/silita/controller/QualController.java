package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.DicAlias;
import com.silita.model.DicCommon;
import com.silita.model.DicQua;
import com.silita.model.RelQuaGrade;
import com.silita.service.IQualService;
import org.apache.commons.collections.MapUtils;
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
        qualService.addQual(qua,JWTUtil.getUsername(request));
        return successMap(null);
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
        qualService.aliasAdd(alias);
        return successMap(null);
    }

    /**
     * 添加资质别名
     * @param alias
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/grade/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> gradeAdd(@RequestBody RelQuaGrade quaGrade, ServletRequest request) {
        return successMap(null);
    }
}
