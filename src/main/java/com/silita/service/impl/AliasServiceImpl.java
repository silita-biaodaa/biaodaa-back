package com.silita.service.impl;

import com.silita.dao.DicAliasMapper;
import com.silita.model.DicAlias;
import com.silita.service.IAliasService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AliasServiceImpl implements IAliasService {

    @Autowired
    DicAliasMapper dicAliasMapper;

    @Override
    public List<DicAlias> getAliasList(Map<String, Object> param) {
        DicAlias dicAlias = new DicAlias();
        dicAlias.setName(MapUtils.getString(param,"name"));
        dicAlias.setStdCode(MapUtils.getString(param,"stdCode"));
        dicAlias.setStdType(MapUtils.getString(param,"stdType"));
        return dicAliasMapper.listDicAliasByStdCode(dicAlias);
    }
}
