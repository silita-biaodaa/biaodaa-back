package com.silita.dao2;

import com.silita.model.ZhaobiaoDetailOthers;
import com.silita.utils.MyMapper;

import java.util.List;

public interface ZhaobiaoDetailOthersMapper extends MyMapper<ZhaobiaoDetailOthers> {

    /**
     * 根据公告pkid获取公告标段信息
     * @param zhaobiaoDetailOthers
     * @return
     */
    List<ZhaobiaoDetailOthers> listZhaobiaoDetailOthersBySnatchUrlId(ZhaobiaoDetailOthers zhaobiaoDetailOthers);

    /**
     * 根据标段pkid更新公告标段信息
     * @param zhaobiaoDetailOthers
     * @return
     */
    Integer updateZhaobiaoDetailOthersById(ZhaobiaoDetailOthers zhaobiaoDetailOthers);
}