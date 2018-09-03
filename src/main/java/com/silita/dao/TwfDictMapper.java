package com.silita.dao;

import java.util.List;
import java.util.Map;

public interface TwfDictMapper {

    /**
     * 根据type获取固定词典表name字段
     * @return
     */
    public List<Map<String, Object>> listTwfDictNameByType(Integer type);
}