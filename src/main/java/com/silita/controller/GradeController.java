package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.DicAlias;
import com.silita.model.DicCommon;
import com.silita.model.RelQuaGrade;
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
     * @return
     */
   /* @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> list() {
        return this.successMap(gradeService.getGradeList(null));
    }*/


    /**
     * 等级列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> listMap(@RequestBody RelQuaGrade relQuaGrade) {
        return this.successMap(gradeService.getDicCommonGradeList(relQuaGrade));
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
        return gradeService.saveGrade(dicCommon, JWTUtil.getUsername(request));
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
        return gradeService.delGrade(param);
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
        return gradeService.addGradeAlias(alias);
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
        return gradeService.updateGradeAlias(alias);
    }

    /**
     * 等级列表
     *
     * @param param
     * @return
     */
   /* @ResponseBody
    @RequestMapping(value = "/cate/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> cateList(@RequestBody Map<String, Object> param) {
        return this.successMap(gradeService.getQualGradeList(param));
    }*/

    /**
     * 资质等级下拉选项
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cate/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> cateListMap(@RequestBody Map<String, Object> param) {
        return this.successMap(gradeService.gitGradePullDownListMap(param));
    }

    /**
     * 二级等级列表
     *
     * @param param
     * @return
     */
    /*@ResponseBody
    @RequestMapping(value = "/sec/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> secList(@RequestBody Map<String, Object> param) {
        return this.successMap(gradeService.getSecQualGradeList(param));
    }*/

    /**
     * 获取符合该资质的等级
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sec/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> secListMap(@RequestBody Map<String,Object> param) {
        return this.successMap(gradeService.getGradeListMap(param));
    }

    /**
     * 添加等级
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/insertGradeLevel", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> insertGradeLevel(@RequestBody Map<String, Object> param,ServletRequest request) {
        String userName = JWTUtil.getUsername(request);
        param.put("createBy", userName);
        return gradeService.insertGradeLevel(param);
    }
    /**
     * 更新等级
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateGradeLevel", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> updateGradeLevel(@RequestBody Map<String, Object> param,ServletRequest request) {
        String userName = JWTUtil.getUsername(request);
        param.put("createBy", userName);
        return gradeService.updateGradeLevel(param);
    }

    /**
     * 删除等级
     * @param param
     * @return
     */
    @RequestMapping(value = "/deleteGradeLevel", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> deleteGradeLevel(@RequestBody Map<String, Object> param) {
        gradeService.deleteGradeLevel(param);
        return this.successMap();
    }

}
