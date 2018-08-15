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

import java.util.*;

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
    public String insertPbModeBySource(DicCommon dicCommon) {
        String msg = null;
        dicCommon.setId(DataHandlingUtil.getUUID());
        String type = dicCommon.getType();
        dicCommon.setType(type + "_pdmode");
        dicCommon.setCode(type + "_pdmode_" + PinYinUtil.cn2py(dicCommon.getName()) + "_" + System.currentTimeMillis());

        Map params = new HashMap<String, Object>(4);
        params.put("type", dicCommon.getType());
        params.put("name", dicCommon.getName());
        params.put("parentId", dicCommon.getParentId());
        params.put("id", dicCommon.getId());
        Integer count = dicCommonMapper.queryDicCommCountByName(params);
        if(count == 0) {
            dicCommonMapper.insertDicCommon(dicCommon);
            msg = "添加评标办法成功！";
        }
        return msg;
    }

    @Override
    public List<DicCommon> listPbModeBySource(DicCommon dicCommon) {
        String type = dicCommon.getType();
        dicCommon.setType(type + "_pdmode");
//        Map<String,Object> result = new HashMap<String, Object>();
//        result.put("total", dicCommonMapper.getDicCommonCountByType(dicCommon));
//        result.put("datas", dicCommonMapper.listDicCommonByType(dicCommon));
//        return super.handlePageCount(result, dicCommon);
        return dicCommonMapper.listDicCommonByType(dicCommon);
    }

    @Override
    public String updatePbModeById(DicCommon dicCommon) {
        String msg = null;
        String type = dicCommon.getType();
        dicCommon.setType(type + "_pdmode");
        dicCommon.setCode(type + "_pdmode_" + PinYinUtil.cn2py(dicCommon.getName()) + "_" + System.currentTimeMillis());

        Map params = new HashMap<String, Object>(4);
        params.put("type", dicCommon.getType());
        params.put("name", dicCommon.getName());
        params.put("parentId", dicCommon.getParentId());
        Integer count = dicCommonMapper.queryDicCommCountByName(params);
        if(count == 0) {
            dicCommonMapper.updateDicCommonById(dicCommon);
            msg = "更新评标办法成功！";
        }
        return msg;
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
    public String insertPbModeAliasByStdCode(DicAlias dicAlias) {
        String msg = null;
        dicAlias.setId(DataHandlingUtil.getUUID());
        dicAlias.setStdType(Constant.PUBLIC_DICTIONARY);
        dicAlias.setCode("alias_pdmode_" + PinYinUtil.cn2py(dicAlias.getName()) + "_" +  System.currentTimeMillis());

        Map params = new HashMap<String, Object>(4);
        params.put("id", dicAlias.getId());
        params.put("name", dicAlias.getName());
        params.put("stdCode", dicAlias.getStdCode());
        Integer count = dicAliasMapper.queryAliasByName(params);
        if(count == 0) {
            dicAliasMapper.insertDicAlias(dicAlias);
            msg = "添加评标办法别名成功！";
        }
        return msg;
    }

    @Override
    public List<DicAlias> listPbModeAliasByStdCode(DicAlias dicAlias) {
        return dicAliasMapper.listDicAliasByStdCode(dicAlias);
    }

    @Override
    public String updatePbModeAliasById(DicAlias dicAlias) {
        String msg = null;
        dicAlias.setStdType(Constant.PUBLIC_DICTIONARY);
        dicAlias.setCode("alias_pdmode_" + PinYinUtil.cn2py(dicAlias.getName()) + "_" +  System.currentTimeMillis());

        Map params = new HashMap<String, Object>(4);
        params.put("name", dicAlias.getName());
        params.put("stdCode", dicAlias.getStdCode());
        Integer count = dicAliasMapper.queryAliasByName(params);
        if(count == 0) {
            dicAliasMapper.updateDicAliasById(dicAlias);
            msg = "更新评标办法别名成功！";
        }
        return msg;
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
