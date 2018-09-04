package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.model.TbNtRecycle;
import com.silita.service.IRecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/recycle")
@Controller
public class RecycleController extends BaseController {

    @Autowired
    IRecycleService recycleService;

    /**
     * 企业列表
     *
     * @param recycleHunan
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> listRecycle(@RequestBody TbNtRecycle recycleHunan) {
        return successMap(recycleService.getRecycleList(recycleHunan));
    }

    /**
     * 批量删除公告
     * @param param
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> delRecycle(@RequestBody Map<String,Object> param){
        recycleService.delRecycel(param);
        return successMap(null);
    }
}
