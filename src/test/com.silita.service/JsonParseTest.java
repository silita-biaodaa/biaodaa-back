package com.silita.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-10-12 15:49
 */
public class JsonParseTest {
    public static void main(String[] args) {
        String jsonStr = "[ { \"name\": [ { \"label\": \"老板\", \"rela\": \"或\" } ], \"label\": \"老板\", \"rela\": \"和\" }, { \"name\": [ { \"label\": \"老板\", \"rela\": \"和\" }, { \"label\": \"经理\", \"rela\": \"或\" } ], \"label\": \"老板\", \"rela\": \"和\" }, { \"name\": [ { \"label\": \"老板\", \"rela\": \"和\" } ], \"label\": \"老板\" } ]";
//        System.out.println(jsonStr);

        JSONArray array = JSON.parseArray(jsonStr);
        for (int i = 0; i < array.size(); i++) {
            JSONObject group = array.getJSONObject(i);
            System.out.println("一个小组" + group);
            String qualStr = group.getString("name");
            JSONArray qualArray = JSON.parseArray(qualStr);
            for (int j = 0; j < qualArray.size(); j++) {

                JSONObject qual = qualArray.getJSONObject(j);
                System.out.println("一条" + qual);
            }
            System.out.println(group.get("rela"));
        }

    }
}
