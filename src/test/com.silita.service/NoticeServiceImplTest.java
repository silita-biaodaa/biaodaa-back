package com.silita.service;

import com.silita.dao.TbNtMianMapper;
import com.silita.model.TbNtMian;
import com.silita.service.impl.NoticeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class NoticeServiceImplTest extends ConfigTest{

    @Autowired
    private TbNtMianMapper tbNtMianMapper;

    TbNtMian tbNtMian = new TbNtMian();

    @Test
    public void listNtMain() {

        Map<String,Object> param = new HashMap<String,Object>();

        param.put("tableName","tb_nt_mian_hunan");
        List<Map<String, Object>> list =
                tbNtMianMapper.listNtMain(tbNtMian);

        for (Map<String, Object> map : list) {
            System.out.println(map);
        }


    }
}