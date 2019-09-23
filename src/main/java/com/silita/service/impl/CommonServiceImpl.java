package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.common.RegionCommon;
import com.silita.dao.DicAliasMapper;
import com.silita.dao.DicCommonMapper;
import com.silita.dao.SysAreaMapper;
import com.silita.service.ICommonService;
import com.silita.utils.oldProjectUtils.CommonUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.SpringApplicationJsonEnvironmentPostProcessor;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

/**
 * common serviceimpl
 */
@Service
public class CommonServiceImpl implements ICommonService {
    @Autowired
    DicCommonMapper dicCommonMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;
    @Autowired
    DicAliasMapper dicAliasMapper;
    /**
     * 获取省/市
     * @return
     */
    @Override
    public List<Map<String, Object>> getArea() {
        List<Map<String, Object>> parentArea = sysAreaMapper.listSysAreaByParentId("0");
        List<Map<String, Object>> cityList = null;
        if (null != parentArea && parentArea.size() > 0) {
            for (Map<String, Object> map : parentArea) {
                cityList = sysAreaMapper.listSysAreaByParentId(MapUtils.getString(map, "pkid"));
                if (null != cityList && cityList.size() > 0) {
                    map.put("citys", cityList);
                }
            }
        }
        return parentArea;
    }

    /**
     * 获取评标办法列表
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> getList(Map<String, Object> param) {
        String source = MapUtils.getString(param, "source");
        if (StringUtil.isEmpty(source)) {
            source = "hunan";
        }
        param.put("type", source + "_pbmode");
        List<Map<String, Object>> list = dicCommonMapper.queryList(param);
        String region = RegionCommon.regionSource.get(source);
        for (Map<String, Object> map : list) {
            map.put("region", region);
        }
        return list;
    }

    /**
     * 批量删除公告词典数据
     *
     * @param param
     */
    @Override
    public void deleteDicCommonIds(Map<String, Object> param) {
        String ids = MapUtils.getString(param, "ids");
        String[] split = ids.split(",");
        List<String> list = Arrays.asList(split);
        param.put("list", list);
        for (String s : list) {
            param.put("id", s);
            String code = dicCommonMapper.queryDicCommonCode(param);
            if (StringUtil.isNotEmpty(code)) {
                param.put("stdCode", code);
                dicAliasMapper.deleteAilas(param);
            }
        }
        dicCommonMapper.deleteDicCommonIds(param);
    }

    /**
     * 修改评标办法名称
     *
     * @param param
     */
    @Override
    public Map<String,Object> updateDicCommonId(Map<String, Object> param) {
        Map<String,Object> resultMap = new HashMap<>();
        String source = MapUtils.getString(param, "source");
        param.put("type",source+"_pbmode");
        Integer integer = dicCommonMapper.queryDicCommonName(param);
        if(null != integer && integer != 0){
            resultMap.put("msg",Constant.MSG_PBMODE);
            resultMap.put("code",Constant.CODE_PBMODE);
            return resultMap;
        }
        dicCommonMapper.updateDicCommonId(param);
        resultMap.put("msg",Constant.MSG_SUCCESS);
        resultMap.put("code",Constant.CODE_SUCCESS);
        return resultMap;
    }
}
