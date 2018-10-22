package com.silita.dao2;

import com.silita.model.AptitudeDictionary;
import com.silita.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

public interface AptitudeDictionaryMapper extends MyMapper<AptitudeDictionary> {

    /**
     * 根据资质uuid获取资质基本信息
     * @param majorUuid
     * @return
     */
    AptitudeDictionary getAptitudeDictionaryByMajorUUid(@Param("majorUuid") String majorUuid);
}