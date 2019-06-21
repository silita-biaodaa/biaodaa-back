package com.silita.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeZhongBiaoServiceImplTest extends ConfigTest{
    @Autowired
    private INoticeZhongBiaoService iNoticeZhongBiaoService;

    @Test
    public void listCompanyTest(){

        String queryKey = "耀邦";
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("queryKey","耀邦");
        List<Map<String, Object>> list =
                iNoticeZhongBiaoService.listCompany(param);

        for (Map<String, Object> map : list) {
            System.out.println(map);
        }
    }
}
