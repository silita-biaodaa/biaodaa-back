package com.silita.service;

import com.silita.dao.TbNtRegexGroupMapper;
import com.silita.model.TbNtRegexGroup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuaTest extends ConfigTest {

    @Autowired
    TbNtRegexGroupMapper tbNtRegexGroupMapper;

    @Test
    public void test() {
        TbNtRegexGroup tbNtRegexGroup = new TbNtRegexGroup();
        tbNtRegexGroup.setNtEditId("1");
        tbNtRegexGroup.setNtEditId("1");
        TbNtRegexGroup ntRegexGroup = tbNtRegexGroupMapper.queryNtRegexGroup(tbNtRegexGroup);
        String groupRegex = ntRegexGroup.getGroupRegex();
        String[] groupRegexs = groupRegex.split("\\|");
        List<List<String>> list = new ArrayList<>(groupRegexs.length);
        for (String str : groupRegexs) {
            if (str.contains("&")) {
                String[] andStr = str.split("&");
                for (String groupStr : andStr) {

                }
            }
        }
        System.out.println(groupRegexs);
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
        lists.add(lable.substring(1, lable.length()));
        return lists;
    }
}
