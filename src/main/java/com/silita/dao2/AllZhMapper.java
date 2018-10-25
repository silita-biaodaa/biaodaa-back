package com.silita.dao2;

import com.silita.model.AllZh;
import com.silita.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AllZhMapper extends MyMapper<AllZh> {

    /**
     * 根据关键字模糊匹配资质别名
     * @param allZh
     * @return
     */
    List<AllZh> listAllZhByName(AllZh allZh);

    /**
     * 根据name获取资质别名
     * @param name
     * @return
     */
    AllZh getAllZhByName(@Param("name") String name);
}