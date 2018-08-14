package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.DicAliasMapper;
import com.silita.dao.DicCommonMapper;
import com.silita.model.DicAlias;
import com.silita.model.DicCommon;
import com.silita.service.IDataMaintainService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.PinYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Autowired
    DicAliasMapper dicAliasMapper;


    @Override
    public Map<String, String> listProvince() {
        return DataHandlingUtil.getpProvinceCode();
    }

    @Override
    public void insertPbModeBySource(DicCommon dicCommon) {
        dicCommon.setId(DataHandlingUtil.getUUID());
        String type = dicCommon.getType();
        dicCommon.setType(type + "_pdmode_");
        dicCommon.setCode(type + "_pdmode_" + PinYinUtil.cn2py(dicCommon.getName()) + "_" + System.currentTimeMillis());
        dicCommonMapper.insertDicCommon(dicCommon);
    }

    @Override
    public List<DicCommon> listPbModeBySource(DicCommon dicCommon) {
//        Map<String,Object> result = new HashMap<String, Object>();
//        result.put("total", dicCommonMapper.getDicCommonCountByType(dicCommon));
//        result.put("datas", dicCommonMapper.listDicCommonByType(dicCommon));
//        return super.handlePageCount(result, dicCommon);
        return dicCommonMapper.listDicCommonByType(dicCommon);
    }

    @Override
    public void updatePbModeById(DicCommon dicCommon) {
        String type = dicCommon.getType();
        dicCommon.setType(type + "_pdmode_");
        dicCommon.setCode(type + "_pdmode_" + PinYinUtil.cn2py(dicCommon.getName()) + "_" +  System.currentTimeMillis());
        dicCommonMapper.updateDicCommonById(dicCommon);
    }

    @Override
    public void deletePbModeByIds(String idStr) {
        String[] ids = idStr.split("\\|");
        Set set = new HashSet<String>();
        for (String id : ids) {
            set.add(id);
        }
        if(set != null && set.size() > 0) {
            dicCommonMapper.deleteDicCommonByIds(set.toArray());
        }
    }



    @Override
    public void insertPbModeAliasByStdCode(DicAlias dicAlias) {
        dicAlias.setId(DataHandlingUtil.getUUID());
        dicAlias.setStdType(Constant.PUBLIC_DICTIONARY);
        dicAlias.setCode("alias_pdmode_" + PinYinUtil.cn2py(dicAlias.getName()) + "_" +  System.currentTimeMillis());
        dicAliasMapper.insertDicAlias(dicAlias);
    }

    @Override
    public List<DicAlias> listPbModeAliasByStdCode(DicAlias dicAlias) {
        return dicAliasMapper.listDicAliasByStdCode(dicAlias);
    }

    @Override
    public void updatePbModeAliasById(DicAlias dicAlias) {
        dicAlias.setStdType(Constant.PUBLIC_DICTIONARY);
        dicAlias.setCode("alias_pdmode_" + PinYinUtil.cn2py(dicAlias.getName()) + "_" +  System.currentTimeMillis());
        dicAliasMapper.updateDicAliasById(dicAlias);
    }

    @Override
    public void deletePbModeAliasByIds(String idStr) {
        String[] ids = idStr.split("\\|");
        Set set = new HashSet<String>();
        for (String id : ids) {
            set.add(id);
        }
        if(set != null && set.size() > 0) {
            dicAliasMapper.deleteDicAliasByIds(set.toArray());
        }
    }
}
