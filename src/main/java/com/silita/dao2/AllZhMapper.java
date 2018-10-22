package com.silita.dao2;

import com.silita.model.AllZh;
import com.silita.utils.MyMapper;

import java.util.List;

public interface AllZhMapper extends MyMapper<AllZh> {

    /**
     * 根据关键字模糊匹配资质别名
     * @param allZh
     * @return
     */
    List<AllZh> listAllZhByName(AllZh allZh);
}