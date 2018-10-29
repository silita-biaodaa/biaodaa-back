package com.silita.controller.base;

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
