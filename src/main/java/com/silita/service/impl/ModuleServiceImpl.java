package com.silita.service.impl;

import com.silita.dao.TbModuleMapper;
import com.silita.dao.TbUserRoleMapper;
import com.silita.service.ModuleService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private TbModuleMapper tbModuleMapper;
    @Autowired
    private TbUserRoleMapper tbUserRoleMapper;
    /**
     * 获取可编辑的权限
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getUpdateUserModule(Map<String, Object> param) {
        param.put("pid", 99);
        List<Map<String, Object>> listMap = new ArrayList<>();

        List<Map<String, Object>> list = tbModuleMapper.queryUpdateUserModule(param);
        for (Map<String, Object> map : list) {
            param.put("pid", MapUtils.getInteger(map, "id"));
            List<Map<String, Object>> listTwo = tbModuleMapper.queryUpdateUserModule(param);
            List<Map<String, Object>> listMap2 = new ArrayList<>();
            for (Map<String, Object> map2 : listTwo) {
                if (MapUtils.getInteger(map, "pid") != 9903) {
                    listMap2.add(map2);
                }
            }
            if (MapUtils.getInteger(map, "id") != 9903) {
                map.put("data", listMap2);
                listMap.add(map);
            }
        }
        return listMap;
    }
    /**
     * 获取可添加权限
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> queryAddUserModule() {
        Map<String, Object> param = new HashMap<>();
        param.put("pid", 99);
        List<Map<String, Object>> listMap = new ArrayList<>();
        try {
            List<Map<String, Object>> list = tbModuleMapper.queryAddUserModule(param);
            for (Map<String, Object> map : list) {
                param.put("pid", MapUtils.getInteger(map, "id"));
                List<Map<String, Object>> listTwo = tbModuleMapper.queryAddUserModule(param);
                List<Map<String, Object>> listMap2 = new ArrayList<>();
                for (Map<String, Object> map2 : listTwo) {
                    if (MapUtils.getInteger(map, "pid") != 9903) {
                        listMap2.add(map2);
                    }
                }
                if (MapUtils.getInteger(map, "id") != 9903) {
                    map.put("data", listMap2);
                    listMap.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMap;
    }

    /**
     * 获取菜单
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> getModule(Map<String, Object> param) {
        Integer integer = tbUserRoleMapper.queryRid(param);
        param.put("rid",integer);
        List<Map<String, Object>> listMap = new ArrayList<>();
        List<Map<String, Object>> list = tbModuleMapper.queryModuleOne();
        for (Map<String, Object> map : list) {
            List<Map<String, Object>> list1 = tbModuleMapper.queryModule(param);
            List<Map<String, Object>> listMap2 = new ArrayList<>();
            for (Map<String, Object> stringObjectMap : list1) {
                if(map.get("id").equals(stringObjectMap.get("pid"))){
                    listMap2.add(stringObjectMap);
                }
            }
            map.put("data",listMap2);
            listMap.add(map);
        }
        List<Map<String,Object>> lists = new ArrayList<>();
        for (Map<String, Object> map : list) {
            List<Map<String, Object>> listMaps = (List<Map<String, Object>>)map.get("data");
            if(listMaps != null && listMaps.size() >0){
                lists.add(map);
            }
        }
        return lists;
    }
}
