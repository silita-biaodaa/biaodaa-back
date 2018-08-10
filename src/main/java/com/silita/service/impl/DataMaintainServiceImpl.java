package com.silita.service.impl;

import com.silita.dao.DicCommonMapper;
import com.silita.model.DicCommon;
import com.silita.service.IDataMaintainService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-10 15:47
 */
@Service("dataMaintainService")
public class DataMaintainServiceImpl extends AbstractService implements IDataMaintainService {

    @Autowired
    DicCommonMapper dicCommonMapper;

    @Override
    public void insertPbModeBySource(DicCommon dicCommon) {
        dicCommon.setId(DataHandlingUtil.getUUID());
        dicCommon.setCode(DataHandlingUtil.getUUID().substring(0, 20));
        String type = DataHandlingUtil.getCode(dicCommon.getType());
        dicCommon.setType(type);
        dicCommonMapper.insertDicCommon(dicCommon);
    }

    @Override
    public void listPbModeBySource(DicCommon dicCommon) {
        dicCommonMapper.listDicCommonByType(dicCommon);
    }

    @Override
    public void updatePbModeById(DicCommon dicCommon) {
        dicCommonMapper.updateDicCommonById(dicCommon);
    }
}
