package com.silita.dao2;

import com.silita.model.ZhongbiaoDetailOthers;
import com.silita.utils.MyMapper;

import java.util.List;

public interface ZhongbiaoDetailOthersMapper extends MyMapper<ZhongbiaoDetailOthers> {

    /**
     * 根据公告pkid获取公告标段信息
     * @param zhongbiaoDetailOthers
     * @return
     */
    List<ZhongbiaoDetailOthers> listZhongbiaoDetailOthersBySnatchUrlId(ZhongbiaoDetailOthers zhongbiaoDetailOthers);

    /**
     * 根据标段pkid更新公告标段信息
     * @param zhongbiaoDetailOthers
     * @return
     */
    Integer updateZhongbiaoDetailOthersById(ZhongbiaoDetailOthers zhongbiaoDetailOthers);
}