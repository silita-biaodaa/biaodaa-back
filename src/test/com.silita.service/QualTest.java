package com.silita.service;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QualTest {

    @Test
    public void test() {
        List<String> list = this.designFormulas(initData());
        System.out.println(list);
    }

    private List<Map<String, Object>> initData() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("label", "资质2");
        map1.put("rela", "&");
        list.add(map1);
        map1 = new HashMap<>();
        map1.put("label", "资质3");
        list.add(map1);
        map1 = new HashMap<>();
        map1.put("label", "资质4");
        list.add(map1);
        map1 = new HashMap<>();
        map1.put("label", "资质5");
        list.add(map1);
        map.put("list", list);
        map.put("label", "资质1");
        mapList.add(map);
        return mapList;
    }

    private List<String> designFormulas(List<Map<String, Object>> list) {
        List<String> lists = new ArrayList<>();
        List<String> shortList = new ArrayList<>();
        String rela = "&";
        List<Map<String, Object>> mapList;
        for (Map<String, Object> map : list) {
            shortList.add(map.get("label").toString());
            if (null != map.get("list")) {
                mapList = (List<Map<String, Object>>) map.get("list");
                if (null != mapList && mapList.size() > 0) {
                    for (int i = 0; i < mapList.size(); i++) {
                        if (i == 0) {
                            if (null != mapList.get(i).get("rela")) {
                                rela = mapList.get(i).get("rela").toString();
                            }
                        }
                        shortList.add(mapList.get(i).get("label").toString());
                    }
                }
            }
        }

        if ("|".equals(rela)) {
            return shortList;
        }
        String lable = "";
        for (String str : shortList) {
            lable = lable + "&" + str;
        }
        lists.add(lable.substring(1,lable.length()));
        return lists;
    }
}
