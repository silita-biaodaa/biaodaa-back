package com.silita.service;

import com.silita.common.MyRedisTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class RedisTest extends ConfigTest {

    @Autowired
    MyRedisTemplate myRedisTemplate;

    @Test
    public void  test(){
       // myRedisTemplate.setObject("ceshikey","filter_map");

        Object sys_area_region = myRedisTemplate.getObject("sys_area_region");

        Map<String,Object> map =  (Map<String,Object>) sys_area_region;

        String countyCode = (String) map.get("xiangtan");

        System.out.println("取到值了："+countyCode);



        for (String s : map.keySet()) {
            System.out.println(s);
        }


    }
}