package com.silita.service.impl;

import com.silita.dao.TbNtTextHunanMapper;
import com.silita.model.TbNtText;
import com.silita.service.INtContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * tb_nt_text serviceimpl
 */
@Service
public class NtContentServiceImpl implements INtContentService {

    @Autowired
    TbNtTextHunanMapper tbNtTextHunanMapper;

    @Override
    public TbNtText getNtContent(TbNtText ntText) {
        TbNtText nt = tbNtTextHunanMapper.queryNtTextDetail(ntText);
        if (null == nt) {
            return new TbNtText();
        }
        return nt;
    }
}
