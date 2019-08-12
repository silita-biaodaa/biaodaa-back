package com.silita.controller.base;

import com.github.pagehelper.PageInfo;
import com.silita.common.Constant;

import java.util.HashMap;
import java.util.Map;

public class BaseController {

    /**
     * 返回code
     *
     * @param obj
     * @return
     */
    public Map<String, Object> successMap(Object obj) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg",Constant.MSG_SUCCESS);
        if (null != obj) {
            resultMap.put("data", obj);
        } else {
            Object[] nullObj = new Object[0];
            resultMap.put("data", nullObj);
        }
        return resultMap;
    }

    public Map<String, Object> successMaps(Object obj) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg",Constant.MSG_SUCCESS);
        if (null != obj) {
            resultMap.put("data", obj);
        } else {
            Object[] nullObj = new Object[0];
            resultMap.put("data", nullObj);
        }
        return resultMap;
    }

    public void seccussMap(Map resultMap, PageInfo pageInfo) {
        resultMap.put("code",Constant.CODE_SUCCESS);
        resultMap.put("msg",Constant.MSG_SUCCESS);
        if (pageInfo != null) {
            resultMap.put("data", pageInfo.getList());
            resultMap.put("pageNo", pageInfo.getPageNum());
            resultMap.put("pageSize", pageInfo.getPageSize());
            resultMap.put("total", pageInfo.getTotal());
            resultMap.put("pages", pageInfo.getPages());
        }
    }



    /**
     * 返回code
     *
     * @return
     */
    public Map<String, Object> successMap() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg",Constant.MSG_SUCCESS);
        return resultMap;
    }
}
