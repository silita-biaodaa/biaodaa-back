package com.silita.controller.base;

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
        resultMap.put("code", 1);
        resultMap.put("msg", "操作成功");
        if (null != obj) {
            resultMap.put("data", obj);
        }
        return resultMap;
    }
}
