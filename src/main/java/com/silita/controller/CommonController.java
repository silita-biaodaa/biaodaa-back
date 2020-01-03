package com.silita.controller;

import com.silita.common.MyRedisTemplate;
import com.silita.controller.base.BaseController;
import com.silita.model.TbNtMian;
import com.silita.service.ICommonService;
import com.silita.service.INoticeZhaoBiaoService;
import com.silita.service.INtContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/common")
@Controller
public class CommonController extends BaseController {

    @Autowired
    MyRedisTemplate myRedisTemplate;
    @Autowired
    ICommonService commonService;
    @Autowired
    INtContentService ntContentService;
    @Autowired
    INoticeZhaoBiaoService noticeZhaoBiaoService;
    private TbNtMian tbNtMian;

    /**
     * 返回地区（省/市）
     *
     * @return
     */
    @RequestMapping(value = "/area", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> area() {
        return successMap(commonService.getArea());
    }

    /**
     * 获取公告原文详情
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> recycelDetail(@RequestBody Map<String,Object> param) throws IOException {
        return successMap(ntContentService.queryCentent(param));
    }
    @RequestMapping(value = "/listRelevantNotice", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> listRelevantNotice(@RequestBody TbNtMian tbNtMian) {
        return super.successMap(noticeZhaoBiaoService.listNtMain(tbNtMian));
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> list(@RequestBody Map<String, Object> param) {
        return super.successMap(commonService.getList(param));
    }

    /**
     * 批量删除公告词典数据
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/deleteDicCommonIds", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> deleteDicCommonIds(@RequestBody Map<String, Object> param) {
        commonService.deleteDicCommonIds(param);
        return super.successMap();
    }

    /**
     * 修改评标名称
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateDicCommonId", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> updateDicCommonId(@RequestBody Map<String, Object> param) {
        return commonService.updateDicCommonId(param);
    }

    /**
     * 修改评标名称
     *
     * @return
     */
    @RequestMapping(value = "/updateRedis", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> updateRedis() {
        Map<String, Object> param = new HashMap<>();
        //Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        String key = "filter_company";
        myRedisTemplate.del(key);
        /*Map<String, Object> notice = new HashMap<>();
        notice.put("bizType", "1");
        Map<String, Object> com = new HashMap<>();
        com.put("bizType", "2");
        List<Map<String, Object>> area = commonService.getAreas();
        List<Map<String, Object>> type = commonService.type();
        List<Map<String, Object>> pbMode = commonService.queryPbModes(param);
        List<Map<String, Object>> noticeList = commonService.queryQua(notice);
        List<Map<String, Object>> comList = commonService.queryQua(com);
        map.put("area", area);
        map.put("type", type);
        map.put("pbMode", pbMode);
        map.put("noticeQua", noticeList);
        map.put("comQua", comList);
        myRedisTemplate.setObject(key,map);*/
        /*seccussMap(resultMap, map);
        return resultMap;*/
        return successMap();
    }
}
