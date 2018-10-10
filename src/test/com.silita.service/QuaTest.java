package com.silita.service;

import com.silita.dao.TbNtQuaGroupMapper;
import com.silita.dao.TbNtRegexGroupMapper;
import com.silita.model.TbNtQuaGroup;
import com.silita.model.TbNtRegexGroup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class QuaTest extends ConfigTest {

    @Autowired
    TbNtRegexGroupMapper tbNtRegexGroupMapper;
    @Autowired
    TbNtQuaGroupMapper tbNtQuaGroupMapper;

    @Test
    public void test() {
        TbNtRegexGroup tbNtRegexGroup = new TbNtRegexGroup();
        tbNtRegexGroup.setNtEditId("1");
        tbNtRegexGroup.setNtEditId("1");
        TbNtRegexGroup ntRegexGroup = tbNtRegexGroupMapper.queryNtRegexGroup(tbNtRegexGroup);
        String groupRegex = ntRegexGroup.getGroupRegex();
        String[] groupRegexs = groupRegex.split("\\|");
        List<List<String>> list = new ArrayList<>(groupRegexs.length);
        TbNtQuaGroup tbNtQuaGroup;
        for (String str : groupRegexs) {
            if (str.contains("&")) {
                String[] andStr = str.split("&");
                List<String> andQuaList = new ArrayList<>();
                for (String groupStr : andStr) {
                    tbNtQuaGroup = new TbNtQuaGroup();
                    tbNtQuaGroup.setGroupId(groupStr);
                    andQuaList.addAll(designFormulas(tbNtQuaGroupMapper.queryNtQuaGroupList(tbNtQuaGroup)));
                }
                list.add(andQuaList);
                continue;
            }
            tbNtQuaGroup = new TbNtQuaGroup();
            tbNtQuaGroup.setGroupId(str);
            list.add(designFormulas(tbNtQuaGroupMapper.queryNtQuaGroupList(tbNtQuaGroup)));
        }
        List<String> groupList = group(list);
        System.out.println(groupList.toString());
    }

    private List<String> designFormulas(List<TbNtQuaGroup> list) {
        List<String> lists = new ArrayList<>();
        List<String> shortList = new ArrayList<>();
        String rela = "&";
        for (int i = 0; i < list.size(); i++) {
            if (null != list.get(i).getRelType()) {
                rela = list.get(i).getRelType();
            }
            shortList.add(list.get(i).getQuaId() + "/" + list.get(i).getQuaGradeId());
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

    private List<String> group(List<List<String>> list) {
        List<String> groupList = new ArrayList<>();
        for (List<String> strList : list) {
            for (String str :strList){
                groupList.add(str);
            }
        }
        return groupList;
    }
}
