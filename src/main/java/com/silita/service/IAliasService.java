package com.silita.service;

import com.silita.model.DicAlias;

import java.util.List;
import java.util.Map;

/**
 * 别名service
 */
public interface IAliasService {

    /**
     * 查询别名
     * @param param
     * @return
     */
    List<DicAlias> getAliasList(Map<String,Object> param);
}
