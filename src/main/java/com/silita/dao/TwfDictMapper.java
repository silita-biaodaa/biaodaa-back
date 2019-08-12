package com.silita.dao;

import com.silita.model.TwfDict;

import java.util.List;
import java.util.Map;

public interface TwfDictMapper {

    /**
     * 根据type获取固定词典表name字段
     * @return
     */
    public List<Map<String, Object>> listTwfDictNameByType(Integer type);

    /**
     * 根据code\type获取名称
     * @param twfDict
     * @return
     */
    public String getNameByCodeAndType(TwfDict twfDict);

    /**
     * 获取项目类型
     * @param
     * @return
     */
    List<Map<String,Object>> queryProType();
}