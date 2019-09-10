package com.silita.service;

import java.util.*;

public class RoleTest {

    public static void main(String[] args) {
        String[] student = {"张三","18","男"};
        List<String> list =
                Arrays.asList(student);
        String[] teacher = {"李老师","28","男"};
        List<String> list2 =
                Arrays.asList(student);

        Map<String,Object> maps = new HashMap<>();
        maps.put("student",list);
        maps.put("teacher ",list2);

        List<Map<String,Object>> listMap = new ArrayList<>();
        listMap.add(maps);

        System.out.println(maps);
        System.out.println(listMap);
    }



}
