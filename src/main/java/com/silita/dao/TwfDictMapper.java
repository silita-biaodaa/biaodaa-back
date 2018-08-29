package com.silita.dao;

import java.util.List;

public interface TwfDictMapper {

    /**
     * 根据type获取固定词典表name字段
     * @return
     */
    public List<String> listTwfDictNameByType(Integer type);
}