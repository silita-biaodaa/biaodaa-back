package com.silita.service.impl;

import com.silita.dao.SysAreaMapper;
import com.silita.service.ICommonService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * common serviceimpl
 */
@Service
public class CommonServiceImpl implements ICommonService {

    @Autowired
    SysAreaMapper sysAreaMapper;

    @Override
    public List<Map<String, Object>> getArea() {
        List<Map<String, Object>> parentArea = sysAreaMapper.listSysAreaByParentId("0");
        List<Map<String, Object>> cityList = null;
        if (null != parentArea && parentArea.size() > 0) {
            for (Map<String, Object> map : parentArea) {
                cityList = sysAreaMapper.listSysAreaByParentId(MapUtils.getString(map, "pkid"));
                map.put("citys", cityList);
            }
        }
        return parentArea;
    }
}
