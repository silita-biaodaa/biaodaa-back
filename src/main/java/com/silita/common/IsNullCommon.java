package com.silita.common;

import org.apache.commons.collections.MapUtils;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Map;

public class IsNullCommon {
    /**
     * 参数判空
     * @param param
     */
    public static void isNull(Map<String,Object> param){

        Integer rid = MapUtils.getInteger(param, "rid");
        if(null == rid){
            param.put("rid","");
        }
        String desc = MapUtils.getString(param, "desc");
        String ids = MapUtils.getString(param, "ids");
        String password = MapUtils.getString(param, "password");
        String realName = MapUtils.getString(param, "realName");
        String phone = MapUtils.getString(param, "phone");
        String department = MapUtils.getString(param, "department");
        String post = MapUtils.getString(param, "post");
        String optType = MapUtils.getString(param, "optType");
        String userType = MapUtils.getString(param, "userType");
        String phoneNo = MapUtils.getString(param, "phoneNo");
        String state = MapUtils.getString(param, "state");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String type = MapUtils.getString(param, "type");
        if(StringUtil.isEmpty(userType)){
            param.put("userType","");
        }
        if(StringUtil.isEmpty(phoneNo)){
            param.put("phoneNo","");
        }
        if(StringUtil.isEmpty(state)){
            param.put("state","");
        }
        if(StringUtil.isEmpty(startDate)){
            param.put("startDate","");
        }
        if(StringUtil.isEmpty(endDate)){
            param.put("endDate","");
        }
        if(StringUtil.isEmpty(type)){
            param.put("type","");
        }
        if(StringUtil.isEmpty(optType)){
            param.put("optType","");
        }
        if(StringUtil.isEmpty(realName)){
            param.put("realName","");
        }
        if(StringUtil.isEmpty(phone)){
            param.put("phone","");
        }
        if(StringUtil.isEmpty(department)){
            param.put("department","");
        }
        if(StringUtil.isEmpty(post)){
            param.put("post","");
        }
        if(StringUtil.isEmpty(desc)){
            param.put("desc","");
        }
        if(StringUtil.isEmpty(ids)){
            param.put("ids","");
        }
        if(StringUtil.isEmpty(password)){
            param.put("password","");
        }
    }

}
