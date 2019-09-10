package com.silita.common;

import com.silita.service.abs.AbstractService;
import com.silita.utils.dateUtils.MyDateUtils;
import org.apache.commons.collections.MapUtils;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Map;

public class UserTypeCommon extends AbstractService {
    /**
     * 用户类型
     * @param integer
     * @param map
     */
    public static void judge(Integer integer, Map<String, Object> map) {
        if (integer != null && integer != 0) {
            String beginTime = MapUtils.getString(map, "expiredDate");
            if (StringUtil.isNotEmpty(beginTime)) {
                Integer compareTo = MyDateUtils.getCompareTo(beginTime);
                if (compareTo < 0) {
                    map.put("userType", "过期");
                } else {
                    if (integer > 1) {
                        map.put("userType", "续费");
                    } else {
                        map.put("userType", "付费");
                    }
                }
            } else {
                map.put("userType", "注册");
            }
        } else {
            map.put("userType", "注册");
        }
    }

}
